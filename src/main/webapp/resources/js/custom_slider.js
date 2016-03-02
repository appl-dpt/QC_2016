/**
 * Slider slicker
 */

jQuery(document).ready(function($) {

	var jssor_1_SlideshowTransitions = [{$Duration:700,$Opacity:2,$Brother:{$Duration:1000,$Opacity:2}}];

	var jssor_1_options = {
		$AutoPlay : true,
		/*$SlideshowOptions : {
			$Class : $JssorSlideshowRunner$,
			$Transitions : jssor_1_SlideshowTransitions,
			$TransitionsOrder : 1
		},*/
		$ArrowNavigatorOptions : {
			$Class : $JssorArrowNavigator$
		},
		$ThumbnailNavigatorOptions : {
			$Class : $JssorThumbnailNavigator$,
			$Cols : 10,
			$SpacingX : 8,
			$SpacingY : 8,
			$Align : 360
		}
	};

	var jssor_1_slider = new $JssorSlider$("jssor_1", jssor_1_options);

	// responsive code begin
	// you can remove responsive code if you don't want the slider scales while
	// window resizing
	function ScaleSlider() {
		var refSize = jssor_1_slider.$Elmt.parentNode.clientWidth;
		if (refSize) {
			refSize = Math.min(refSize, 600);
			jssor_1_slider.$ScaleWidth(refSize);
		} else {
			window.setTimeout(ScaleSlider, 30);
		}
	}
	ScaleSlider();
	$(window).bind("load", ScaleSlider);
	$(window).bind("resize", ScaleSlider);
	$(window).bind("orientationchange", ScaleSlider);
	// responsive code end
});