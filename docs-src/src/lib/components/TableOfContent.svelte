<script lang="ts">
	import { onMount, onDestroy } from 'svelte';
	import { browser } from '$app/environment';
	import { writable } from 'svelte/store';
	import CtrlBtn from './CtrlBtn.svelte';

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
		margin: 0em;
		padding: 1em;
		border: 1px solid #ccc;
		border-radius: 2px;
		color: #111;
		background-color: #eee;
	}
	.toc .content ol {
		/* cosmetic */
		padding-left: 1em;
		padding-top: 0em;
	}
	.toc .content ol li a {
		/* cosmetic */
		text-decoration: none;
	}
</style>

<div class="toc no-print" class:expanded={$isContentVisible}>
	<CtrlBtn text="ToC" hoverText="Table of Content" on:click={toggleTableOfContent} isSelected={$isContentVisible} />

	{#if $isContentVisible}
	<div class="content">
		<ol>
			<li><a href="./onebrc"    >Title</a></li>
			<li><a href="./overview"  >Overview</a></li>
			<li><a href="./overview-2">Overview (2)</a></li>
			<li><a href="./results"   >Results</a></li>
			<li><a href="./challenge" >One Billion Row Challenge</a></li>
			<li><a href="./solution"  >Solution Overview</a></li>
		</ol>
	</div>
	{/if}
</div>
