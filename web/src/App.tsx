import Router from './router'
import './App.scss'
import ViewportProvider from './content/viewportContent'

const App = () => {
  return (
    <ViewportProvider>
      <div>
          <Router/>
      </div>
    </ViewportProvider>
  )
}

export default App