import React from 'react'
import ReactDOM from 'react-dom/client'
import App from '@/App'
import {BrowserRouter} from 'react-router-dom'
import store from './store'
import 'reset-css'
import { Provider } from 'react-redux'
import { PersistGate } from 'redux-persist/integration/react'
import { persistStore } from 'redux-persist'

let persistor = persistStore(store);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <BrowserRouter>
          <App/>
        </BrowserRouter>
      </PersistGate>
    </Provider> 
  </React.StrictMode>,
)
