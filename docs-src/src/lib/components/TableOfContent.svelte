<script lang="ts">
	import { onMount, onDestroy } from 'svelte';
	import { browser } from '$app/environment';
	import { writable } from 'svelte/store';
	import CtrlBtn from './CtrlBtn.svelte';

	let tocRef: HTMLElement;
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
	function handleClickOutside(event: MouseEvent) {
		if (tocRef && !tocRef.contains(event.target as Node)) {
			turnOffTableOfContent();
		}
	}

	onMount(() => {
		if (browser) {
			window.addEventListener('keydown', handleGlobalKeydown);
			document.addEventListener('click', handleClickOutside);
		}
	});

	onDestroy(() => {
		if (browser) {
			window.removeEventListener('keydown', handleGlobalKeydown);
			document.removeEventListener('click', handleClickOutside);
		}
	});
</script>

<div class="toc no-print" class:expanded={$isContentVisible} bind:this={tocRef}>
	<CtrlBtn text="Table of Content" hoverText="Table of Content" on:click={toggleTableOfContent} isSelected={$isContentVisible} />

	{#if $isContentVisible}
	<div class="content">
		<div id="article"><a href="../text.html">View as article</a></div>
		<ol>
			<li><a href="./onebrc.html"    >Title</a></li>
			<li><a href="./overview.html"  >Overview</a></li>
			<li><a href="./overview-2.html">Overview (2)</a></li>
			<li><a href="./results.html"   >Results</a></li>
			<li><a href="./challenge.html" >One Billion Row Challenge</a></li>

			<li><a href="./solution.html"                     >Solution</a></li>
			<li><a href="./solution-1-multithread.html"       >Solution (1): Multithread</a></li>
			<li><a href="./solution-2-memory-mapped-file.html">Solution (2): Memory-Mapped File</a></li>
			<li><a href="./solution-3-string-conversion.html" >Solution (3): Delay String Conversion</a></li>
			<li><a href="./solution-4-name-as-byte-array.html">Solution (4): Name as Byte Array</a></li>
			<li><a href="./solution-5-value-as-integer.html" >Solution (5): Value as Integer</a></li>
			<li><a href="./solution-6-vectorize-compare.html" >Solution (6): Vectorize Comparison</a></li>
			<li><a href="./solution-7-name-normalization.html">Solution (7): Name Normalization</a></li>

			<li><a href="./conclusion.html">Conclusion</a></li>
		</ol>
	</div>
	{/if}
</div>

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
		padding-left: 2em; /* The number will take some space so we have to prepare extra space. */
		padding-right: 1em;
		padding-top: 0em;
		padding-bottom: 0em;
		margin: 0em;
		margin-left: 0.2em;
		border: 1px solid #ccc;
		border-radius: 2px;
		color: #111;
		background-color: #eee;
	}

	.toc .content ol {
		/* cosmetic */
		padding: 0px;
	}

	a {
		/* cosmetic */
		text-decoration: none;
	}

	.toc .content ol li a {
		/* cosmetic */
		padding-left: 0.5em;
		padding-right: 0.5em;
	}
	#article:hover,
	.toc .content ol li:hover {
		background-color: #ddd;
	}
</style>
