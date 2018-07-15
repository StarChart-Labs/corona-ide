// Modules to control application life and create native browser window
const { app, BrowserWindow, Menu, globalShortcut } = require('electron');

// Keep a global reference of the window object, if you don't, the window will
// be closed automatically when the JavaScript object is garbage collected.
let mainWindow;

function createMenuBar() {
  const template = [
    {
      label: 'Corona',
      submenu: [
        {
          label: 'About Corona',
          click() {
              // TODO implement About dialog
          }
        },
        {
          type: 'separator'
        },
        {
          label: 'Quit',
          accelerator: 'CmdOrCtrl+Q',
          click() {
            app.quit();
          }
        }
      ]
    },
    {
      label: 'File',
      submenu: [
        {
          label: 'New Project',
          accelerator: 'CmdOrCtrl+N',
          click() {
              // TODO implement New Project dialog
          }
        }
      ]
    },
    {
      label: 'View',
      submenu: [
        {
          label: 'Open Dev Tools',
          accelerator: 'CmdOrCtrl+Alt+I',
          click(item, focusedWindow) {
            if (focusedWindow) focusedWindow.webContents.toggleDevTools();
          }
        }
      ]
    }
  ];
  Menu.setApplicationMenu(Menu.buildFromTemplate(template));
}

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', () => {
  // Create the browser window.
  mainWindow = new BrowserWindow({width: 1000, height: 800});

  // Loads the realtime React dev server!
  // need to make this configurable for production situations
  // https://medium.freecodecamp.org/building-an-electron-application-with-create-react-app-97945861647c
  mainWindow.loadURL('http://localhost:3000');

  // Emitted when the window is closed.
  mainWindow.on('closed', function () {
    // Dereference the window object, usually you would store windows
    // in an array if your app supports multi windows, this is the time
    // when you should delete the corresponding element.
    mainWindow = null
  });

  createMenuBar();
});

// Quit when all windows are closed.
app.on('window-all-closed', function () {
  app.quit();
});
