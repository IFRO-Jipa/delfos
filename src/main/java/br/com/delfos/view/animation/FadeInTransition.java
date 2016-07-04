package br.com.delfos.view.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.scene.Node;
import javafx.util.Duration;

@SuppressWarnings("deprecation")
// TODO: Melhorar a implementação. É uma legítima gambiarra
public class FadeInTransition extends AbstractAnimation {
	public FadeInTransition(final Node node) {
		super(node,
		        TimelineBuilder.create()
		                .keyFrames(new KeyFrame(Duration.millis(0), new KeyValue(node.opacityProperty(), 0, WEB_EASE)),
		                        new KeyFrame(Duration.millis(1000), new KeyValue(node.opacityProperty(), 1, WEB_EASE)))
		                .build());
		setCycleDuration(Duration.seconds(2));
		setDelay(Duration.seconds(0.2));

	}
}
