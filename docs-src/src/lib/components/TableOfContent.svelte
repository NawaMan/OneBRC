<script lang="ts">
	import { onMount, onDestroy } from 'svelte';
	import { browser } from '$app/environment';
	import CtrlBtn from './CtrlBtn.svelte';
	import { writable } from 'svelte/store';

	let isContentVisible = writable(false);

	function toggleTableOfContent() {
		isContentVisible.update(value => !value);
	}
	function turnOffTableOfContent() {
		isContentVisible.update(__ => false);
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
		top: 0px;
		left:0px;
		margin:0px;
		padding: 0px;
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
</style>

<div class="toc" class:expanded={$isContentVisible}>
	<CtrlBtn text="ToC" hoverText="Table of Content" on:click={toggleTableOfContent} isSelected={$isContentVisible} />

	{#if $isContentVisible}
	<div class="content">
		<p>This is the hidden content that appears when the square is clicked.</p>
	</div>
	{/if}
</div>
