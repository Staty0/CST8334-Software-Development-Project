/**
 * 
 */
/**
 * 
 */
module solitaire {
	requires javafx.base;
    requires javafx.controls;
	requires transitive javafx.graphics;
	requires javafx.fxml;
	
    exports gui;
    opens gui to javafx.graphics;
    exports controller;
    opens controller to javafx.fxml;
    exports model;
 
}