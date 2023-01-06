import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ToDoList extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof StackPane)) {
            return false;
        }
        StackPane s = (StackPane) o;
        HBox h = (HBox)s.getChildren().get(1);
        StackPane s1 = (StackPane)h.getChildren().get(0);
		StackPane s2 = (StackPane)h.getChildren().get(1);
		StackPane s3 = (StackPane)h.getChildren().get(2);

		Text t1 = (Text)s1.getChildren().get(1);
		Text t2 = (Text)s2.getChildren().get(1);
		Text t3 = (Text)s3.getChildren().get(1);

		String name = t1.getText(); //of parameter
		String type = t2.getText(); //of parameter
		String hours = t3.getText();//of parameter

		//now we have to get name, type, and hours of "this"
		//first we need to acknowleddge that "this" is of time ToDoList
		//do some climbing?
		//need to get to ObservableList 


		/*
		StackPane thisS = (StackPane)this;
		HBox thisH = (HBox)thisS.getChildren().get(1);
		StackPane thisS1 = (StackPane)thisH.getChildren().get(0);
		StackPane thisS2 = (StackPane)thisH.getChildren().get(1);
		StackPane thisS3 = (StackPane)thisH.getChildren().get(2);

		Text thisT1 = (Text)thisS1.getChildren().get(1);
		Text thisT2 = (Text)thisS2.getChildren().get(1);
		Text thisT3 = (Text)thisS3.getChildren().get(1);

		String thisName = thisT1.getText(); //of this
		String thisType = thisT2.getText(); //of this
		String thisHours = thisT3.getText();//of this
		*/

		//now compare all three fields

        return true;
	}

	//function that returns singular task 
	public StackPane taskToBeCompleted(String taskNameString, int hours, String taskTypeString) {
		HBox taskInfo = new HBox(30);

		StackPane taskNameBg = new StackPane();
		Text taskName = new Text(taskNameString);
		taskName.setWrappingWidth(230);
		double height = taskName.getLayoutBounds().getHeight();
		int numOfLines = (int)Math.round(height / 15); 
		int rectHeight = 0;
		if (taskName.getText().equals("")) {
			rectHeight = 30;
		} else {
			rectHeight = (15 * numOfLines) + 20;
		}
		Rectangle taskNameBacking = new Rectangle(250 ,rectHeight);		
		taskNameBacking.setArcWidth(30.0);
		taskNameBacking.setArcHeight(30.0);
		taskNameBacking.setStyle("-fx-fill: #ffffff");
		taskNameBg.getChildren().addAll(taskNameBacking, taskName);

		StackPane taskTypeBg = new StackPane();
		Text taskType = new Text(taskTypeString);
		Rectangle taskTypeBacking = new Rectangle(150 ,30);
		taskTypeBacking.setArcWidth(30.0);
		taskTypeBacking.setArcHeight(30.0);
		taskTypeBacking.setStyle("-fx-fill: #ffffff");
		taskTypeBg.getChildren().addAll(taskTypeBacking, taskType);

		StackPane dueDateBg = new StackPane();
		Text dueDate = dueDate(hours);
		Text invisibleHours = new Text(String.valueOf(hours));
		invisibleHours.setFill(Color.WHITE);
		Rectangle dueDateBacking = new Rectangle(260,30);
		dueDateBacking.setArcWidth(30.0);
		dueDateBacking.setArcHeight(30.0);
		dueDateBacking.setStyle("-fx-fill: #ffffff");
		dueDateBg.getChildren().addAll(dueDateBacking, invisibleHours, dueDate); //we have now changed the due date index from 1 to 2 what does this break???

		taskInfo.getChildren().addAll(taskNameBg, taskTypeBg, dueDateBg);
		taskInfo.setAlignment(Pos.CENTER);
		
		Rectangle taskOverallBg = new Rectangle(750, rectHeight + 30);
		taskOverallBg.setArcWidth(60.0);
		taskOverallBg.setArcHeight(60.0);
		taskOverallBg.setStyle("-fx-fill: #20B2AA;");

		StackPane taskOverall = new StackPane();
		taskOverall.getChildren().addAll(taskOverallBg, taskInfo);
		return taskOverall;
	}
	
	public Text dueDate(int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, hours);
		Text dueDate = new Text(calendar.getTime().toString());
		return dueDate;
	}

	public String todaysDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/uuuu");
      	LocalDate localDate = LocalDate.now();
      	return dtf.format(localDate);
	}
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("To Do List");

		Pane layout = new Pane();
		layout.setStyle("-fx-background-color: #3B3737"); 

		//Enqueue and Dequeue VBox
		Button enqueue = new Button("Enqueue");
		Button dequeue = new Button("Dequeue");

		enqueue.setPrefSize(250, 70);
		dequeue.setPrefSize(250, 70);

		enqueue.setStyle("-fx-font-size: 25;-fx-background-color: #20B2AA;-fx-background-radius: 15px;-fx-text-fill: #ffffff;");
		dequeue.setStyle("-fx-font-size: 25;-fx-background-color: #20B2AA;-fx-background-radius: 15px;-fx-text-fill: #ffffff;");

		enqueue.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//climb out and then back down to where we need to go
				Button target = (Button)e.getTarget();
				VBox parent1 = (VBox)target.getParent();
				Pane root = (Pane)parent1.getParent();
				StackPane taskEnteringBg = (StackPane)root.getChildren().get(2);
				HBox taskEntering = (HBox)taskEnteringBg.getChildren().get(1);
				ListView tasksDisplay = (ListView)root.getChildren().get(1);
				
				TextField taskNameTextField = (TextField)taskEntering.getChildren().get(0);
				ComboBox taskHoursComboBox = (ComboBox)taskEntering.getChildren().get(1);
				ComboBox taskTypeComboBox = (ComboBox)taskEntering.getChildren().get(2);
				ObservableList<StackPane> taskList = tasksDisplay.getItems();

				VBox taskCounter = (VBox)root.getChildren().get(3);
				StackPane remainingPanel = (StackPane)taskCounter.getChildren().get(0);
				StackPane completedPanel = (StackPane)taskCounter.getChildren().get(1);

				VBox remainingInfoText = (VBox)remainingPanel.getChildren().get(1);
				VBox completedInfoText = (VBox)completedPanel.getChildren().get(1);

				Text numOfRemaining = (Text)remainingInfoText.getChildren().get(1);
				Text numOfCompleted = (Text)completedInfoText.getChildren().get(1);

				String taskNameString = taskNameTextField.getText();

				int hours;
				boolean hoursNotSelected = taskHoursComboBox.getSelectionModel().isEmpty();

				if (hoursNotSelected) {
					hours = -1;
				} else {
					hours = Integer.parseInt(taskHoursComboBox.getValue().toString());
				}

				String taskTypeString;
				boolean taskTypeNotSelected = taskTypeComboBox.getSelectionModel().isEmpty();

				if (taskTypeNotSelected) {
					taskTypeString = "NO_TASK_TYPE";
				} else {
					taskTypeString = taskTypeComboBox.getValue().toString();
				}

				Alert a = new Alert(Alert.AlertType.INFORMATION);

				if (taskNameString.equals(null) || taskNameString.equals("")) {
					a.setContentText("Enter the name of the task.");
					a.showAndWait();
				} else if (hours == -1) {
					a.setContentText("Select how long the task will take.");
					a.showAndWait();
				} else if (taskTypeString.equals("NO_TASK_TYPE")) {
					a.setContentText("Select the type of the task");
					a.showAndWait();
				} else {
					taskNameTextField.clear(); 
					taskHoursComboBox.setValue(null);
					taskTypeComboBox.setValue(null);
					StackPane task = taskToBeCompleted(taskNameString, hours, taskTypeString);
					if (taskList.contains(task)) {
						a.setAlertType(AlertType.WARNING);
						a.setContentText("This task already exists");
						a.show();
					} else {
						taskList.add(task);
						numOfRemaining.setText(Integer.toString(taskList.size()));
					}
				}
			}
		});

		dequeue.setOnAction(
			e -> {
				Button target = (Button)e.getTarget();
				VBox parent1 = (VBox)target.getParent();
				Pane root = (Pane)parent1.getParent();

				VBox taskCounter = (VBox)root.getChildren().get(3);
				StackPane remainingPanel = (StackPane)taskCounter.getChildren().get(0);
				StackPane completedPanel = (StackPane)taskCounter.getChildren().get(1);

				VBox remainingInfoText = (VBox)remainingPanel.getChildren().get(1);
				VBox completedInfoText = (VBox)completedPanel.getChildren().get(1);

				Text numOfRemaining = (Text)remainingInfoText.getChildren().get(1);
				Text numOfCompleted = (Text)completedInfoText.getChildren().get(1);


				ListView tasksDisplay = (ListView)root.getChildren().get(1);
				ObservableList<StackPane> taskList = tasksDisplay.getItems();

				taskList.remove(0);
				int completed = Integer.parseInt(numOfCompleted.getText());
				int remaining = Integer.parseInt(numOfRemaining.getText());

				numOfCompleted.setText(Integer.toString(++completed));
				numOfRemaining.setText(Integer.toString(--remaining));
			}
		);

		VBox queue = new VBox(20);
		queue.setLayoutX(900);
		queue.setLayoutY(700);

		queue.getChildren().addAll(enqueue, dequeue);

		//Remaining StackPane	
		Text remaining = new Text("Remaining");
		Text numOfRemaining = new Text("0");

		Rectangle remainingBackground = new Rectangle(250, 250);
		remainingBackground.setArcWidth(45.0);
		remainingBackground.setArcHeight(45.0);

		remaining.setStyle("-fx-font-size: 40;");
		remaining.setFill(Color.WHITE);
		numOfRemaining.setStyle("-fx-font-size: 100;");
		numOfRemaining.setFill(Color.WHITE);
		remainingBackground.setStyle("-fx-fill: #20B2AA;");

		VBox remainingInfoText = new VBox(20);
		remainingInfoText.getChildren().addAll(remaining, numOfRemaining);
		remainingInfoText.setAlignment(Pos.CENTER);

		StackPane remainingPanel = new StackPane();
		remainingPanel.getChildren().addAll(remainingBackground, remainingInfoText);


		//Completed StackPane
		Text completed = new Text("Completed");
		Text numOfCompleted = new Text("0");
		Rectangle completedBackground = new Rectangle(250, 250);
		completedBackground.setArcWidth(45.0);
		completedBackground.setArcHeight(45.0);

		completed.setStyle("-fx-font-size: 40;");
		completed.setFill(Color.WHITE);
		numOfCompleted.setStyle("-fx-font-size: 100;");
		numOfCompleted.setFill(Color.WHITE);
		completedBackground.setStyle("-fx-fill: #20B2AA;");

		VBox completedInfoText = new VBox(20);
		completedInfoText.getChildren().addAll(completed, numOfCompleted);
		completedInfoText.setAlignment(Pos.CENTER);

		StackPane completedPanel = new StackPane();
		completedPanel.getChildren().addAll(completedBackground, completedInfoText);
		
		//Remaining and Completed VBox
		VBox taskCounter = new VBox(20);
		taskCounter.getChildren().addAll(remainingPanel, completedPanel);
		taskCounter.setLayoutX(900); 
		taskCounter.setLayoutY(150); 

		//Task Entering StackPane --> HBox

		HBox taskEntering = new HBox(25);
		TextField taskName = new TextField();
		taskName.setId("TASK_NAME_ENTERING_FIELD");
		taskName.setPrefSize(400, 50);

		ComboBox hours = new ComboBox();
		hours.getItems().add("1");
		hours.getItems().add("2");
		hours.getItems().add("3");
		hours.getItems().add("4");
		hours.getItems().add("5");
		hours.setPrefSize(150, 50);

		ComboBox type = new ComboBox();
		type.getItems().add("Study");
		type.getItems().add("Shop");
		type.getItems().add("Cook");
		type.getItems().add("Sleep");
		type.setPrefSize(150, 50);

		Rectangle taskEnteringBackground = new Rectangle(800, 100);
		taskEnteringBackground.setArcWidth(100.0);
		taskEnteringBackground.setArcHeight(100.0);

		taskName.setStyle("-fx-background-radius: 25px;");
		hours.setStyle("-fx-background-radius: 25px;");
		type.setStyle("-fx-background-radius: 25px;");
		taskEnteringBackground.setStyle("-fx-fill: #20B2AA;");


		taskEntering.getChildren().addAll(taskName, hours, type);
		taskEntering.setAlignment(Pos.CENTER);

		StackPane taskEnteringBackgroundPane = new StackPane();
		taskEnteringBackgroundPane.getChildren().addAll(taskEnteringBackground, taskEntering);

		taskEnteringBackgroundPane.setLayoutX(50);
		taskEnteringBackgroundPane.setLayoutY(725);

		//ListView object for the actual list of things to do taskList
		
		ObservableList<StackPane> taskList = FXCollections.<StackPane>observableArrayList();
		ListView<StackPane> tasks = new ListView<>(taskList);

		tasks.setStyle("-fx-control-inner-background: #3B3737");
		tasks.setOrientation(Orientation.VERTICAL);
		tasks.setPrefSize(800, 520);
		tasks.setLayoutX(50);
		tasks.setLayoutY(150);

		//HBox to go into Title Pane
		HBox titleInfo = new HBox(20);

		StackPane calendarDate = new StackPane();
		Rectangle calendarDateBg = new Rectangle(230, 60);
		calendarDateBg.setStyle("-fx-fill: #ffffff");
		calendarDateBg.setArcWidth(60);
		calendarDateBg.setArcHeight(60);
		Text dateText = new Text(todaysDate());
		dateText.setStyle("-fx-font-size: 30");
		calendarDate.getChildren().addAll(calendarDateBg, dateText);

		StackPane title = new StackPane();
		Rectangle titleBg = new Rectangle(280 ,60);
		titleBg.setStyle("-fx-fill: #ffffff");
		titleBg.setArcWidth(60);
		titleBg.setArcHeight(60);
		Text titleText = new Text("Ansh's To Do List");
		titleText.setStyle("-fx-font-size: 30");
		title.getChildren().addAll(titleBg, titleText);

		StackPane time = new StackPane();
		Rectangle timeBg = new Rectangle(230, 60);
		timeBg.setStyle("-fx-fill: #ffffff");
		timeBg.setArcWidth(60);
		timeBg.setArcHeight(60);
		Text timeText = new Text();
		Timer myTimer = new Timer();

	    myTimer.schedule(new TimerTask() {
	    	@Override
	    	public void run() {
	        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
				LocalDateTime now = LocalDateTime.now();
				timeText.setText(dtf.format(now));  
				
	        }
	    }, 0, 1000);

	    timeText.setStyle("-fx-font-size: 30");
		time.getChildren().addAll(timeBg, timeText);

		titleInfo.getChildren().addAll(calendarDate, title, time);
		titleInfo.setAlignment(Pos.CENTER);

		//Title Pane
		StackPane titlePane = new StackPane();
		Rectangle titlePaneBg = new Rectangle(830, 100);
		titlePaneBg.setArcWidth(100.0);
		titlePaneBg.setArcHeight(100.0);
		titlePaneBg.setStyle("-fx-fill: #20B2AA;");
		titlePane.getChildren().addAll(titlePaneBg, titleInfo);
		titlePane.setLayoutX(185);
		titlePane.setLayoutY(25);

		//add to master pane
		layout.getChildren().add(titlePane);
		layout.getChildren().add(tasks);
		layout.getChildren().add(taskEnteringBackgroundPane);
		layout.getChildren().add(taskCounter);
		layout.getChildren().add(queue);

		Scene scene = new Scene(layout, 1200, 900);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}