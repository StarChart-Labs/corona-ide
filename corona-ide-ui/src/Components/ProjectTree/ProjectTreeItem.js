import React, { Component } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { ContextMenuTrigger, ContextMenu, MenuItem } from "react-contextmenu";
import '../../Style/ProjectTree.css';

class ProjectTreeItem extends Component {

  constructor(props) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick() {
    this.props.projectDeleteHandler(this.props.name, this.props.path)
  }

  render() {
    return (
      <React.Fragment>
         <ContextMenuTrigger id={`tree-context-menu-${this.props.path}`} holdToDisplay={-1}>
           <div className={`project-row${this.props.selected ? ' selected' : ''}`} onClick={this.props.onClick}>
             <FontAwesomeIcon icon='folder' />
             <div>{this.props.name}</div>
           </div>
         </ContextMenuTrigger>
         <ContextMenu id={`tree-context-menu-${this.props.path}`}>
           <MenuItem onClick={this.handleClick}>
             Delete
           </MenuItem>
        </ContextMenu>
      </React.Fragment>
    );
  }
}

export default ProjectTreeItem;
