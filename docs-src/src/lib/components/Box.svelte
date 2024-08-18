<script lang="ts">
	export let expanded = false; // Control expansion
	export let width; // Image width
	export let height; // Image height
	export let linkUrl: string | null = null; // Link
	export let linkText = 'LINK'; // Link
	export let onClick: (() => void) | null = null;

	function collapse() {
		expanded = false;
	}
</script>

<div class="parent {expanded ? 'expanded' : ''}">
	<div
		class="img-box"
		style="--full-width: {width}px; --full-height: {height}px;"
		on:click={onClick}
		on:keydown={onClick}
		role="button"
		tabindex="-1">
		<slot />
		<div class="close" on:click={collapse} on:keydown={collapse} role="button" tabindex="-1">
			CLOSE
		</div>

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
	}

    .parent .link a {
        text-decoration: none;
    }

    .parent .close,
    .parent .link {
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
		overflow: hidden;
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
			0 0 0 9999px rgb(0, 0, 0, 0.5);
		transition:
			var(--box-ani-time) calc(0 * var(--box-ani-time)) width,
			var(--box-ani-time) calc(1 * var(--box-ani-time)) height,
			var(--box-ani-time) calc(0 * var(--box-ani-time)) margin-left,
			var(--box-ani-time) calc(0 * var(--box-ani-time)) padding,
			var(--box-ani-time) calc(0 * var(--box-ani-time)) border,
			var(--box-ani-time) calc(0 * var(--box-ani-time)) box-shadow;
	}
</style>
