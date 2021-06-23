package ieexp3.id190441148;

import ieexp3.library.CaoAPI;
import javafx.concurrent.Task;

public class RobotControlTask3 extends Task<Void> {
	public String HoleNo1;
	public String HoleNo2;
	public String HoleNo3;

	public boolean RunFlag = false;

	/* バックグラウンド・スレッドで呼び出されるメソッド*/
	@Override
	protected Void call() throws Exception {
		try {
			// CAOエンジンの初期化
			updateMessage("Connect!");
			CaoAPI.init("TestWorkspace");
			System.out.println("CAO engine is initialized.");

			// コントローラに接続
			CaoAPI.connect("RC8", "VE026A");
			System.out.println("Controller and Robot are connected.");

			// 自動モードに設定
			CaoAPI.setControllerMode(CaoAPI.CONTROLLER_MODE_AUTO);
			System.out.println("Operation mode is set to Auto mode.");

			// モータを起動
			CaoAPI.turnOnMotor();
			System.out.println("Motor is turned on.");

			// ロボットの外部速度/加速度/減速度を設定
			float speed = 50.0f, accel = 25.0f, decel = 25.0f;
			CaoAPI.setExtSpeed(speed, accel, decel);
			System.out.println("External speed/acceleration/deceleration is set to "+ speed + "/" + accel + "/" + decel + ".");

			// ロボット操作

			updateMessage("\nPress Run Button when ready.");
			while(RunFlag == false) {
				Thread.sleep(1000);
			}
			updateMessage("Run");



			// TakeArm Keep = 0
			CaoAPI.takeArm(0L, 0L);

			// Speed 100
			CaoAPI.speed(-1L, 100.0f);

			// Move P, @0 P1
			CaoAPI.move(1L, "@0 P0", "");


			// Approach P, P2, @0 70
			// Approach P, P2, @0 0
			// DriveA (7, F1)		※DriveAコマンドの目標位置は数値で指定すること
			// DriveA (7, F2)
			// Approach P, P2, @0 70
			CaoAPI.approach(1L, "P"+ HoleNo1, "@0 70", "");
			CaoAPI.approach(1L, "P"+ HoleNo1, "@0 0", "");
			CaoAPI.driveAEx("(7, -15)", "");
			CaoAPI.driveAEx("(7, -50)", "");
			CaoAPI.approach(1L, "P"+ HoleNo1, "@0 70", "");

			// Move P, @0 P1
			CaoAPI.move(1L, "@0 P0", "");
			// Approach P, P3, @0 70
			// Approach P, P3, @0 0
			// DriveA (7, F1)		※DriveAコマンドの目標位置は数値で指定すること
			// DriveA (7, F2)
			// Approach P, P3, @0 70
			CaoAPI.approach(1L, "P"+ HoleNo2, "@0 70", "");
			CaoAPI.approach(1L, "P"+ HoleNo2, "@0 0", "");
			CaoAPI.driveAEx("(7, -15)", "");
			CaoAPI.driveAEx("(7, -50)", "");
			CaoAPI.approach(1L, "P"+ HoleNo2, "@0 70", "");

			// Move P, @0 P1
			CaoAPI.move(1L, "@0 P0", "");
			// Approach P, P4, @0 70
			// Approach P, P4, @0 0
			// DriveA (7, F1)		※DriveAコマンドの目標位置は数値で指定すること
			// DriveA (7, F2)
			// Approach P, P3, @0 70
			CaoAPI.approach(1L, "P"+ HoleNo3, "@0 70", "");
			CaoAPI.approach(1L, "P"+ HoleNo3, "@0 0", "");
			CaoAPI.driveAEx("(7, -15)", "");
			CaoAPI.driveAEx("(7, -50)", "");
			CaoAPI.approach(1L, "P"+ HoleNo3, "@0 70", "");

			// Move P, @0 P1
			CaoAPI.move(1L, "@0 P0", "");

			// GiveArm
			CaoAPI.giveArm();

			updateMessage("finish");

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				// モータを停止
				CaoAPI.turnOffMotor();
				System.out.println("Moter is turned off.");

				// コントローラから切断
				CaoAPI.disconnect();
				System.out.println("Controller and Robot is disconnected.");
			} catch (Exception e) {
				e.printStackTrace();
			}

			updateMessage("finish");
		}

		return null;
	}

	/**
	 * タスクの状態遷移がSUCCEEDED状態になった時に呼び出されるメソッド
	 */
	@Override
	protected void succeeded() {
		super.succeeded();
		updateMessage("Done!");
	}

	/**
	 * タスクの状態遷移がCANCELLED状態になった時に呼び出されるメソッド
	 */
	@Override
	protected void cancelled() {
		super.cancelled();
		updateMessage("Cancelled!");
	}

	/**
	 * タスクの状態遷移がFAILED状態になった時に呼び出されるメソッド
	 */
	@Override
	protected void failed() {
		super.failed();
		updateMessage("Failed!");
	}
}

