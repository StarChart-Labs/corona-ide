import React, { Component } from 'react';
import ProjectTreeItem from './ProjectTreeItem';
import '../../Style/ProjectTree.css';

class ProjectTree extends Component {
  constructor() {
    super();
    this.state = {
      projects: [],
      selectedPath: ''
    }
    this.projectListItemClick = this.projectListItemClick.bind(this);
  }

  componentDidMount() {
    fetch('/projects')
      .then(result => {return result.json()})
      .then(result => {
        this.setState({projects: result});
      })
      .catch(err => console.log(err));
  }

  projectListItemClick(key) {
    this.setState({selectedPath: key});
  }

  render() {
    let projectsList = [];
    for(const project of this.state.projects) {
      projectsList.push(<ProjectTreeItem key={project.rootDirectory}
              name={project.name}
              selected={this.state.selectedPath === project.rootDirectory}
              onClick={() => this.projectListItemClick(project.rootDirectory)}/>);
    }

    return (
      <div className="project-tree">
        {projectsList}
      </div>
    );
  }
}

export default ProjectTree;
