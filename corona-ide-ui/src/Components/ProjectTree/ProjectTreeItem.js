import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import '../../Style/ProjectTree.css';

class ProjectTreeItem extends Component {

  render() {
    return (
      <div className={`project-row${this.props.selected ? ' selected' : ''}`} onClick={this.props.onClick}>
        <FontAwesomeIcon icon='folder' />
        <div>{this.props.name}</div>
      </div>
    );
  }
}

export default ProjectTreeItem;
