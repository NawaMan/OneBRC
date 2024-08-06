<script lang="ts">
	import { onMount, onDestroy } from 'svelte';
	import { browser } from '$app/environment';

	let isContentVisible = false;

	function toggleTableOfContent() {
		isContentVisible = !isContentVisible;
	}
	function turnOffTableOfContent() {
		isContentVisible = false;
	}
	function handleGlobalKeydown(event: KeyboardEvent) {
		if (event.ctrlKey && event.altKey && event.key === 'T') {
			event.preventDefault();
			toggleTableOfContent();
		}
		if (event.key === 'Escape') {
			event.preventDefault();
			turnOffTableOfContent();
		}
	}

	onMount(() => {
		if (browser) {
			window.addEventListener('keydown', handleGlobalKeydown);
		}
	});

	onDestroy(() => {
		if (browser) {
			window.removeEventListener('keydown', handleGlobalKeydown);
		}
	});
</script>

<style>
	.toc {
		/* functional */
		position: absolute;
		top: 5px;
		left:0px;
		margin:0px;
	}
	.toc button {
		/* functional */
		cursor: pointer;

		/* cosmetic */
		width: 40px;
		height: 22px;
		padding-left: 0.5em;
		padding-right: 0.5em;
		margin-left: 0.5em;
		text-justify: center;
		text-align: center;
		background-color: #333;
		color: #888;
		font-weight: bold;
		border: 0px;
	}

	.toc button:active {
		/* cosmetic */
		box-shadow: 0 2px #0056b3;
		transform: translateY(3px);
	}

	.toc button:hover {
		/* cosmetic */
		color: #fff;
		background-color: #2980b9;
	}

	.toc .content {
		/* cosmetic */
		margin-left: 0.5em;
		margin-top: 0.5em;
		padding: 5px;
		border: 1px solid #ccc;
		border-radius: 2px;
		color: #111;
		background-color: #eee;
	}
	.toc.expanded button {
		/* cosmetic */
		color: #fff;
		background-color: green;
	}
</style>

<div class="toc" class:expanded={isContentVisible}>
	<button
		tabindex="-1"
		on:click={toggleTableOfContent}
		on:keydown={toggleTableOfContent}
		aria-expanded={isContentVisible}
		aria-controls="hiddenContent"
	>ToC</button>

	{#if isContentVisible}
	<div class="content">
		<p>This is the hidden content that appears when the square is clicked.</p>
	</div>
	{/if}
</div>
