import './App.css';
import './Canvas.css';

function CanvasLegend(props) {

    let canvas
    let ctx

    const setup = () => {
        canvas = document.getElementsByClassName("colorCanvas" + props.color)[0];
        console.log(canvas)
        ctx = canvas.getContext("2d");

        ctx.lineWidth = 2;
        ctx.strokeStyle = props.color;

        ctx.beginPath()

        ctx.moveTo(0, 0)
        ctx.lineTo(100, 100);

        ctx.stroke();
        // canvas.style.backgroundColor = "black"
    }

    // window.onload = () => {
    //     // setup()
    // };

  return (
    <div className="CanvasLegend">
      <header className="CanvasLegend-header">
          <div style={{
              border : "solid 0.5px " + props.color,
              transform : "rotate(-20deg)",
          }}>
          </div>
          {/*<canvas id={"colorCanvas"} className={"colorCanvas" + props.color} width={"30px"} height={"30px"}/>*/}
      </header>
    </div>
  );
}

export default CanvasLegend;
