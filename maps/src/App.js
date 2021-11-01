import './App.css';
import Route from "./Route";

function App() {

    document.title = "Dora the Explorer";

  return (
    <div className="App">
      <header className="App-header">
          <Route/>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
        </a>
      </header>
    </div>
  );
}

export default App;
