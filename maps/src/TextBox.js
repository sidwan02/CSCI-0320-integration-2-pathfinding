import './App.css';

function TextBox(props) {

    const myFunction = (event) => {
        console.log(event.target.value.replace("C:\\fakepath\\", ""))
        props.change(event.target.value.replace("C:\\fakepath\\", ""))
        return event.target.value.replace("C:\\fakepath\\", "");
    }

  return (
    <div className="TextBox">
      <header className="TextBox-header">
          {props.label}
          <input type={props.type} onChange={myFunction}/>
      </header>
    </div>
  );
}

export default TextBox;
