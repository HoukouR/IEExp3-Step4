package ieexp3.id190441148;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BasicTask3Controller {

	@FXML private Label labelState;
	@FXML private TextField textHole1;
	@FXML private TextField textHole2;
	@FXML private TextField textHole3;
	@FXML private Button buttonConnect;
	@FXML private Button buttonDisconnect;
	@FXML private Button buttonRun;

	private RobotControlTask3 task;

	@FXML
	protected void handleButtonConnectAction(ActionEvent event) {
		// タスクが存在する場合は削除してから生成（取得した値をタスクへ渡す）
		if (task != null)
		task = null;
		task = new RobotControlTask3();

		// タスクのメッセージプロパティと状態ラベルのテキストプロパティを紐付け
		labelState.textProperty().bind(task.messageProperty());
		// タスクの動作プロパティとStartボタンの無効プロパティを紐付け
		buttonConnect.disableProperty().bind(task.runningProperty());
		// タスクの動作プロパティの逆値とStopボタンの無効プロパティを紐付け
		buttonDisconnect.disableProperty().bind(task.runningProperty().not());

		// タスクをバックグラウンドスレッドで実行
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	@FXML
	protected void handleButtonDisConnectAction(ActionEvent event) {
		// タスクを中断
		task.cancel();

		// タスクの各プロパティとGUIコンポーネントとの紐付けを解消
		labelState.textProperty().unbind();
		buttonConnect.disableProperty().unbind();
		buttonDisconnect.disableProperty().unbind();
	}

	@FXML
	protected void  handleButtonRunAction(ActionEvent event) {
		task.HoleNo1 = textHole1.getText();
		task.HoleNo2 = textHole2.getText();
		task.HoleNo3 = textHole3.getText();
		task.RunFlag = true;
	}


}
