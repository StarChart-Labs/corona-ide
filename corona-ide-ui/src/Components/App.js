import React, { Component } from 'react';
import ProjectTree from './ProjectTree/ProjectTree'
import '../Style/App.css';

import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';
library.add(fas);

class App extends Component {
  render() {
    return (
      <div className="App">
        <ProjectTree />
        <div className='editor-area'>
          Editor not yet implemented
        </div>
      </div>
    );
  }
}

export default App;
