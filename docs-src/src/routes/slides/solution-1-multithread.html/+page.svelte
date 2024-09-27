<script lang="ts">
	import Box from '$lib/components/Box.svelte';
	import Hint from '$lib/components/Hint.svelte';
	import NavigationBar from '$lib/components/NavigationBar.svelte';
	import WideDiv from '$lib/components/WideDiv.svelte';
	import ContentPage from '$lib/templates/ContentPage.svelte';
	import { onMount } from 'svelte';

	let hint = "scroll to pan";

	let isCodeExpanded = false;

	function toggleCodeExpanded() {
		isCodeExpanded = !isCodeExpanded;
		console.log("isCodeExpanded: " + isCodeExpanded);
	}

	let svgContent = '';
	async function fetchSVG() {
		const response = await fetch('../Solution-Diagram.svg');
		svgContent
				= (await response.text())
				.replaceAll("url('virgil.ttf')",             "url('../virgil.ttf')")
				.replaceAll("width=\"3830.640967790001\"",   "width=\"1973\"")
				.replaceAll("height=\"1048.6091475027065\"", "width=\"540\"");
	}

	onMount(() => {
		fetchSVG();
	});
</script>

<ContentPage title="Solution (1): Mutiple-Thread Processing">
	<WideDiv
		outerWidth="1190"
		innerWidth="2000"
		height="500"
		startScrollPosition={0}
		style="border: 2px #C0F1FF solid; border-radius: 2px; height: 550px; background-color: #181818;">

		<div id="diagram">
			{#if svgContent}
			{@html svgContent}
			{/if}
		</div>
	</WideDiv>
	<div id="side">
		<div id="side-content">
			<ul>
				<li>Split the data into chunks</li>
				<li>Process on separate threads</li>
				<li>Optimized at 32 x CPU count</li>
				<li>Use Java 21 "Virtual threads"</li>
				<li>Extract concurrently</li>
				<li>Combine concurrently</li>
				<li>Normalize names concurrently</li>
			</ul>
			<button class="thumbnail" on:click={toggleCodeExpanded}>
				<img src="../multithreads.png" alt="main()" width="350"/>
			</button>
		</div>
	</div>
</ContentPage>
<Hint text="{hint}" />
<NavigationBar 
	prevLink="./solution.html" 
	nextLink="./solution-2-memory-mapped-file.html"
/>
<Box expanded={isCodeExpanded} width={952} height={664} onClick={toggleCodeExpanded}>
    <img src="../multithreads.png" alt="main()" width="952px" height="664px"/>
</Box>

<style>
	#diagram {
		position: relative;
	}
	#side {
		position: absolute;
		left:   800px;
		top:    110px;
		width:  432px;
		height: 580px;
		background-color: #181818;
		border-radius: 10px;
		box-shadow: 0 0 20px 20px rgba(0, 0, 0, 0.8);
	}
	#side-content {
		margin-top: 15px;
		margin-left: 10px;
		margin-right: 10px;
		width: 100wv;
	}
	.thumbnail {
		margin-left: 20px;
		cursor: pointer;
		border-radius: 5px;
		border: 2px solid #C0F1FF;
	}
</style>
