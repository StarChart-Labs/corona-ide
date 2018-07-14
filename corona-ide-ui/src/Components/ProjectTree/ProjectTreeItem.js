import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { ContextMenuTrigger, ContextMenu, MenuItem } from "react-contextmenu";
import '../../Style/ProjectTree.css';

class ProjectTreeItem extends Component {

  render() {
    return (
      <React.Fragment>
        <ContextMenuTrigger id='tree-context-menu' holdToDisplay='-1'>
          <div className={`project-row${this.props.selected ? ' selected' : ''}`} onClick={this.props.onClick}>
            <FontAwesomeIcon icon='folder' />
            <div>{this.props.name}</div>
          </div>
        </ContextMenuTrigger>
        <ContextMenu id='tree-context-menu'>
          <MenuItem onClick={() => console.log(this.props.path)}>
            Delete
          </MenuItem>
        </ContextMenu>
      </React.Fragment>
    );
  }
}

export default ProjectTreeItem;
