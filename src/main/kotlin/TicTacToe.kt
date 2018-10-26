import javafx.application.Application
import javafx.application.Application.launch
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import javafx.scene.shape.Rectangle
import javafx.scene.paint.Color

class TicTacToeApp: Application(){
    override fun start(primaryStage: Stage) {
        primaryStage.scene = Scene(createContent())
        primaryStage.show()
    }

    fun createContent() : Parent {
        //Setup the main window
        val window = Pane()
        window.setPrefSize(600.0, 600.0)
        //Create the TicTacToe squares for the window
        for(i in 0..3){
            for(j in 0..3){
                val square = TTTSquare()
                square.translateX = j*200.0
                square.translateY = i*200.0
                window.children.add(square)
            }
        }
        return window
    }

    //Used to create the Tic Tac Toe squares inside of the main window
    private class TTTSquare: StackPane() {
        init {
            val border = Rectangle(200.0,200.0)
            border.fill = null
            border.stroke = Color.BLACK
            setAlignment(Pos.CENTER)
            children.add(border)
        }
    }
}
fun main(args: Array<String>) {
    launch(TicTacToeApp::class.java)
}