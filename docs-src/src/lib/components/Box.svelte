<script lang="ts">
	import { onMount } from "svelte";

	export let width;
	export let height;

	export let expanded   : boolean       = false;
	export let showClose  : boolean       = true;
	export let scrollable : boolean       = false;
	export let top        : string | null = null;
	export let left       : string | null = null;
	export let bottom     : string | null = null;
	export let right      : string | null = null;
	export let linkUrl    : string | null = null;
	export let linkText   : string        = 'LINK';

	export let shadowOpacity: number              = 0.5;
	export let onClick      : (() => void) | null = null;

	function collapse() {
		expanded = false;
	}

	onMount(() => {
		window.addEventListener('keydown', collapse);

		return () => {
		window.removeEventListener('keydown', collapse);
		};
	});
</script>

<div
	class="parent {expanded ? 'expanded' : ''}"
	style="--custom-top: {top}; --custom-left: {left}; --custom-bottom: {bottom}; --custom-right: {right}; --shadow-opacity: {shadowOpacity}; --scrollable: {scrollable ? 'scroll' : 'hidden'};"
>
	<div
		class="img-box"
		style="--full-width: {width}px; --full-height: {height}px;"
		on:click={onClick}
		on:keydown={onClick}
		role="button"
		tabindex="-1">
		<slot />
		{#if showClose}
		<div class="close" on:click={collapse} on:keydown={collapse} role="button" tabindex="-1">
			CLOSE
		</div>
		{/if}

		{#if linkUrl}
			<div class="link"><a href={linkUrl} target="_blank">{linkText}</a></div>
		{/if}
	</div>
</div>

<style>
	.parent {
		--box-ani-time: 0.3s;
		display: block;
		position: absolute;
		width: 0px;
		height: 0px;
		top: var(--custom-top, 50%);
		left: var(--custom-left, 50%);
		bottom: var(--custom-bottom, auto);
		right: var(--custom-right, auto);
		transform: translate(-50%, -50%);
	}

    .parent .link a {
        text-decoration: none;
    }

    .parent .close,
    .parent .link {
		font-size: small;
        position: absolute;
        overflow: hidden;
        cursor: pointer;
		background-color: lightblue;
		color: black;
        border-radius: 3px;
        align-content: center;
        text-align: center;
        top: calc(50% - var(--full-height) / 2);
        transition: var(--box-ani-time) height;
    }

	.parent .close {
		right: calc(100% - 6px - 0.5 * var(--full-width));
		width: 5.5em;
		padding: 0px;
	}

    .parent .link {
        left: calc(50% + 3px - var(--full-width) / 2);
        padding-top: 0px;
        padding-bottom: 0px;
        padding-left: 1em;
        padding-right: 1em;
    }

	.parent:not(.expanded) .close,
    .parent:not(.expanded) .link {
		height: 0em;
		transition:
            /* Duration         Delay */
			var(--box-ani-time) calc(0 * var(--box-ani-time)) top,
			var(--box-ani-time) calc(0 * var(--box-ani-time)) height,
			var(--box-ani-time) calc(0 * var(--box-ani-time)) padding;
	}
    
	.parent.expanded .close {
		top: calc(50% - 1.5em - 3px - var(--full-height) / 2);
		height: 1.5em;
		padding: 3px;
		transition:
            /* Duration         Delay */
			var(--box-ani-time) calc(1 * var(--box-ani-time)) top,
			var(--box-ani-time) calc(1 * var(--box-ani-time)) height,
			var(--box-ani-time) calc(1 * var(--box-ani-time)) padding;
	}

	.parent.expanded .link {
		top: calc(50% - 1.5em - 3px - var(--full-height) / 2);
		height: 1.5em;
		padding-top: 3px;
		padding-bottom: 3px;
		padding-left: 1em;
		padding-right: 1em;
		transition:
            /* Duration         Delay */
			var(--box-ani-time) calc(1 * var(--box-ani-time)) top,
			var(--box-ani-time) calc(1 * var(--box-ani-time)) height,
			var(--box-ani-time) calc(1 * var(--box-ani-time)) padding;
	}

	.parent .img-box {
		padding: 0px;
		overflow-x: hidden;
		overflow-y: var(--scrollable);
		background-color: black;

		border: 0px solid lightblue;
		width: 0px;
		height: 0px;
		margin-top: calc(50% - var(--full-height) / 2);
		margin-left: 50%;
		box-shadow:
			2px 2px 0px black,
			0 0 0 9999px rgb(0, 0, 0, 0);
		transition:
            /* Duration         Delay */
			var(--box-ani-time) calc(1 * var(--box-ani-time)) width,
			var(--box-ani-time) calc(0 * var(--box-ani-time)) height,
			var(--box-ani-time) calc(1 * var(--box-ani-time)) margin-left,
			var(--box-ani-time) calc(1 * var(--box-ani-time)) padding,
			var(--box-ani-time) calc(1 * var(--box-ani-time)) border,
			var(--box-ani-time) calc(1 * var(--box-ani-time)) box-shadow;
	}

	.parent.expanded .img-box {
		border-radius: 3px;
		border: 5px solid lightblue;
		width: var(--full-width);
		height: var(--full-height);
		margin-top: calc(50% - var(--full-height) / 2);
		margin-left: calc(50% - var(--full-width) / 2);
		box-shadow:
			2px 2px 10px black,
			0 0 0 9999px rgb(0, 0, 0, var(--shadow-opacity));
		transition:
			var(--box-ani-time) calc(0 * var(--box-ani-time)) width,
			var(--box-ani-time) calc(1 * var(--box-ani-time)) height,
			var(--box-ani-time) calc(0 * var(--box-ani-time)) margin-left,
			var(--box-ani-time) calc(0 * var(--box-ani-time)) padding,
			var(--box-ani-time) calc(0 * var(--box-ani-time)) border,
			var(--box-ani-time) calc(0 * var(--box-ani-time)) box-shadow;
	}
</style>
