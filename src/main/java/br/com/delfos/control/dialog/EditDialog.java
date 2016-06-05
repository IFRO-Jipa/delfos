package br.com.delfos.control.dialog;

import javafx.stage.Stage;

public interface EditDialog<T> {
	boolean isOkCliked();

	void setDialogStage(Stage stage);

	void setValue(T value);

	T getValue();

}
