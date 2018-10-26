import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class TicTacToeApp: Application(){

    fun createContent() : Parent {
        val root = Pane()
        root.setPrefSize(600.0, 600.0)
        return root
    }

    override fun start(primaryStage: Stage) {
        primaryStage.scene = Scene(createContent())
        primaryStage.show()
    }


}
fun main(args: Array<String>) {
    launch(TicTacToeApp::class.java)
}