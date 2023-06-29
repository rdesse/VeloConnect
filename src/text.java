public void changeScene(Scene scene) {
	primaryStage.setMinWidth(1024);
    primaryStage.setMinHeight(768);
    primaryStage.setMaxWidth(1024);
    primaryStage.setMaxHeight(768);
    primaryStage.setScene(scene);
    primaryStage.show();
}