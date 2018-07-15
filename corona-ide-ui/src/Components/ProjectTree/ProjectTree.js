import React, { Component } from 'react';
import { Modal } from '@starchart-labs/react-flightdeck';
import ProjectTreeItem from './ProjectTreeItem';
import '../../Style/ProjectTree.css';

class ProjectTree extends Component {
  constructor() {
    super();
    this.state = {
      projects: [],
      selectedPath: '',
      deleteProjectModalOpen: false,
      projectToDelete: {
        name: '',
        key: ''
      },
      deleteProjectFromDisk: false
    }
    this.fetchProjects = this.fetchProjects.bind(this);
    this.projectListItemClick = this.projectListItemClick.bind(this);
    this.showDeleteProjectModal = this.showDeleteProjectModal.bind(this);
    this.closeDeleteProjectModal = this.closeDeleteProjectModal.bind(this);
    this.deleteProject = this.deleteProject.bind(this);

    this.handleDeleteCheckboxChange = this.handleDeleteCheckboxChange.bind(this);
  }

  fetchProjects() {
    fetch('/projects')
      .then(result => {return result.json()})
      .then(result => {
        this.setState({projects: result});
      })
      .catch(err => console.log(err));
  }

  componentDidMount() {
    this.fetchProjects();
  }

  showDeleteProjectModal(name, key) {
    this.setState({
      deleteProjectModalOpen: true,
      projectToDelete: {
        name,
        key
      }
    });
  }

  closeDeleteProjectModal() {
    this.setState({
      deleteProjectModalOpen: false,
      projectToDelete: {
        name: '',
        key: ''
      },
      deleteProjectFromDisk: false
    });
  }

  projectListItemClick(key) {
    this.setState({selectedPath: key});
  }

  deleteProject() {
    fetch('/projects', {
      method: 'DELETE',
      headers: {
          'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        rootDirectory: this.state.projectToDelete.key,
        deleteFromDisk: this.state.deleteProjectFromDisk
      })
    })
    .then(() => {
      this.fetchProjects();
      this.closeDeleteProjectModal();
    })
    .catch(e => {
      console.log(e);
      this.fetchProjects();
      this.closeDeleteProjectModal();
    })
  }

  handleDeleteCheckboxChange(e) {
    this.setState({
      deleteProjectFromDisk: e.target.checked
    });
  }

  render() {
    let projectsList = [];
    for(const project of this.state.projects) {
      projectsList.push(<ProjectTreeItem key={project.rootDirectory}
              name={project.name}
              path={project.rootDirectory}
              selected={this.state.selectedPath === project.rootDirectory}
              onClick={() => this.projectListItemClick(project.rootDirectory)}
              projectDeleteHandler={this.showDeleteProjectModal}/>);
    }

    return (
      <div className="project-tree">
        <Modal title='Delete Project'
          open={this.state.deleteProjectModalOpen}
          onClose={this.closeDeleteProjectModal}
          content={
            <React.Fragment>
              <p>Are you sure you want to remove the project {this.state.projectToDelete.name} from the workspace?</p>
              <label>
                <input type='checkbox' checked={!!this.state.deleteProjectFromDisk} name='deleteFromDisk' onChange={this.handleDeleteCheckboxChange} />
                Delete project from disk (This cannot be undone!)
              </label>
            </React.Fragment>
          }
          buttons={[
             <button key='delete-button' className='button' onClick={this.deleteProject}>Confirm</button>,
             <button key='close-button' className='button' onClick={this.closeDeleteProjectModal}>Close</button>,
          ]}
        />
        {projectsList}
      </div>
    );
  }
}

export default ProjectTree;
