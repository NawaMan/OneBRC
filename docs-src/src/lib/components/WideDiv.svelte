<script lang="ts">
	export let outerWidth: string;
	export let innerWidth: string;
	export let height:     string;
    export let startScrollPosition: number           = 0;
	export let minScrollPosition:   number|undefined = undefined;
    export let maxScrollPosition:   number|undefined = undefined;

	let scrollPosition = startScrollPosition;

	function handleScroll(event: WheelEvent) {
        if (minScrollPosition === undefined) minScrollPosition = parseInt(innerWidth) * -0.25;
        if (maxScrollPosition === undefined) maxScrollPosition = parseInt(innerWidth) *  0.75;

		if (event.deltaX != 0) {
			scrollPosition += event.deltaX;
		} else {
			scrollPosition += event.deltaY;
		}
        scrollPosition = Math.max(minScrollPosition, scrollPosition);
        scrollPosition = Math.min(maxScrollPosition, scrollPosition);
	}
</script>

<div class="outer" 
     on:wheel={handleScroll} 
     style="width: {outerWidth}px; height: {height}px;" 
     {...$$restProps}>
	<div
		class="inner"
		style="width: {innerWidth}px; height: {height}px; --scroll-position: {scrollPosition};">
		<slot></slot>
	</div>
</div>

<style>
	.outer {
		overflow: hidden;
		position: relative;
	}

	.inner {
		position: absolute;

		top:  0;
		left: 0;

		transform:  translateX(calc(var(--scroll-position) * -1px));
		transition: transform 0.1s ease-out;
	}
</style>
