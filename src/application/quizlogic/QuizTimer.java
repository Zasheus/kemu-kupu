package application.quizlogic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class QuizTimer {

    private final Label timeLabel;
    private Timeline timeline;
    private int time = 0;

    public QuizTimer(Label timeLabel) {
        this.timeLabel = timeLabel;
        setUpTimer();
    }

    /**
     * Method to setup the timer
     */
    private void setUpTimer() {
        addTimelineForTimer();
        timeLabel.setText("Time Elapsed: " + time);
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Method to initialise the timeline to update the timer once per second
     */
    private void addTimelineForTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            time++;
            timeLabel.setText("Time Elapsed: " + time);
        }));
    }

    /**
     * Method to update the time and the displayed timer label
     * This method will be called everytime the quiz moves to a new word
     * @param isSkipped if the word was skipped or not
     */
    public void updateTimerAndResults(Quiz quiz, boolean isSkipped) {
        ((GameQuiz) quiz).saveTimeAndResult(isSkipped, time);
        time = 0;
        timeLabel.setText("Time Elapsed: " + time);
    }

    public void pauseTimer() {
        timeline.stop();
    }

    public void resumeTimer() {
        timeline.play();
    }
}
