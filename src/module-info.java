/**
 * 
 */
/**
 * 
 */
module solitaire {
	requires javafx.base;
    requires javafx.controls;
	requires javafx.graphics;
    exports gui;
    opens gui to javafx.graphics;
}