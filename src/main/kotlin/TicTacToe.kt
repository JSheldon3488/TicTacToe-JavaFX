import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
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
import javafx.scene.shape.Line
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.util.Duration

//I think there might be a better way to store the board and check for winners
//Need a different way to check for winners

class TicTacToeApp: Application(){
    var playerTurn = "X"
    var playable = true
    var board = Array<Array<TTTSquare?>>(3, {arrayOfNulls<TTTSquare?>(3)})
    val winningCombos = arrayListOf<Combo>()
    val window = Pane()

    fun createContent() : Parent {
        //Setup the main window
        window.setPrefSize(600.0, 600.0)

        //Create the TicTacToe squares for the window and initialize baord
        for(i in 0..2){
            for(j in 0..2){
                val square = TTTSquare()
                square.translateX = j*200.0
                square.translateY = i*200.0
                square.centerX = j*200.0 + 100
                square.centerY = i*200.0 + 100
                window.children.add(square)
                board[j][i] = square
            }
        }
        //Setup winningCombos variable to be used later
        //All Horizontals
        for(y in 0..2){
            winningCombos.add(Combo(board[0][y]!!, board[1][y]!!, board[2][y]!!)) }
        //All Vertical
        for(x in 0..2){
            winningCombos.add(Combo(board[x][0]!!, board[x][1]!!, board[x][2]!!)) }
        //All Diagonals
        winningCombos.add(Combo(board[0][0]!!, board[1][1]!!, board[2][2]!!))
        winningCombos.add(Combo(board[2][0]!!, board[1][1]!!, board[0][2]!!))

        return window
    }

    override fun start(primaryStage: Stage) {
        primaryStage.scene = Scene(createContent())
        primaryStage.show()
    }

    fun checkState() {
        for (combo in winningCombos){
            if (combo.isWinner()){
                playable = false
                playWinAnimation(combo)
                break
            }
        }
    }
    fun playWinAnimation(combo: Combo ){
        //Create a line and add it to the children on the window so it can be used
        val line = Line()
        line.startX = combo.tilesToCheck[0].centerX
        line.startY = combo.tilesToCheck[0].centerY
        line.endX = combo.tilesToCheck[0].centerX
        line.endY = combo.tilesToCheck[0].centerY
        line.strokeWidth = 4.0
        if (playerTurn == "X"){ line.stroke = Color.GREEN }
        if (playerTurn == "O"){ line.stroke = Color.RED }
        window.children.add(line)

        //Animation Timeline
        val timeline = Timeline()
        timeline.keyFrames.add(KeyFrame(Duration.seconds(1.0),
                KeyValue(line.endXProperty(), combo.tilesToCheck[2].centerX),
                KeyValue(line.endYProperty(), combo.tilesToCheck[2].centerY)))
        timeline.play()
    }

    //Used to create the Tic Tac Toe squares inside of the main window
    inner class TTTSquare: StackPane() {
        var text = Text()
        var centerX : Double = 0.0
        var centerY : Double = 0.0

        init {
            val border = Rectangle(200.0,200.0)
            border.fill = null
            border.stroke = Color.BLACK
            setAlignment(Pos.CENTER)
            text.font = Font.font(88.0)
            children.addAll(border, text)
            //Sets the call on the Node itself instead of needing X and Y coordinates
            setOnMouseClicked { if (playable) draw() }
        }
        fun draw() {
            if (playerTurn == "X"){
                text.fill = Color.GREEN
                text.text = "X"
                checkState()
                playerTurn = "O"
            }
            else {
                text.fill = Color.RED
                text.text = "O"
                checkState()
                playerTurn = "X"
            }
        }
        fun getValule(): String {
            return text.text
        }

    }
    //I feel like this could potentially be refactored and the functionality doesn't need to be in it's own class
    class Combo(vararg tiles: TTTSquare) {
        var tilesToCheck = arrayListOf(*tiles)

        fun isWinner(): Boolean {
            if (tilesToCheck[0].getValule().isEmpty()) return false
            return (tilesToCheck[0].getValule().equals(tilesToCheck[1].getValule())
                    && tilesToCheck[1].getValule().equals(tilesToCheck[2].getValule()))
        }
    }
}

fun main(args: Array<String>) {
    launch(TicTacToeApp::class.java)
}