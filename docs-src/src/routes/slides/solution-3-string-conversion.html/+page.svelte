<script lang="ts">
	import Box from '$lib/components/Box.svelte';
	import Hint from '$lib/components/Hint.svelte';
	import NavigationBar from '$lib/components/NavigationBar.svelte';
	import WideDiv from '$lib/components/WideDiv.svelte';
	import ContentPage from '$lib/templates/ContentPage.svelte';
	import { onMount } from 'svelte';

	let hint = "scroll to pan";

	let isCodeOneExpanded = false;
	let isCodeTwoExpanded = false;

	function toggleCodeOneExpanded() {
		isCodeOneExpanded = !isCodeOneExpanded;
	}
	function toggleCodeTwoExpanded() {
		isCodeTwoExpanded = !isCodeTwoExpanded;
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

<ContentPage title="Solution (3): Delay String Conversion">
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
				<li>Read from file as part of a chunk</li>
				<li>Recorgnized and extracted as name</li>
				<li>Looked up in the map as a key</li>
				<li class="needed">Used to sort (as UTF8)</li>
				<li class="needed">Print out</li>
			</ul>
			Only the last two steps that the name <b>must</b> be a string.<br />
			
			<div id="thumbnails">
				<button class="thumbnail" on:click={toggleCodeTwoExpanded}>
					<img src="../name-equals.png" alt="main()" width="136"/>
				</button>
			</div>
		</div>
	</div>
</ContentPage>
<Box expanded={isCodeTwoExpanded} width={698} height={650} onClick={toggleCodeTwoExpanded} scrollable={true}>
    <img src="../name-equals.png" alt="main()" width="698px" height="1200px"/>
</Box>
<NavigationBar
	prevLink="./solution-2-memory-mapped-file.html"
	nextLink="./solution-4-one-pass-parsing.html"
/>

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
		font-size: 20px;
		background-color: #181818;
		border-radius: 10px;
		box-shadow: 0 0 20px 20px rgba(0, 0, 0, 0.8);
	}
	#side-content {
		padding-top: 20px;
		padding-left: 30px;
		padding-right: 30px;
	}
	#side-content ul {
		padding-top: 0px;
		padding-bottom: 20px;
		padding-left: 20px;
		margin: 0px;
	}
	#thumbnails {
		display: flex;
		justify-content: center;
		padding-top: 20px;
	}
	.thumbnail {
		margin-left: 5px;
		cursor: pointer;
		border-radius: 5px;
		border: 2px solid #C0F1FF;
	}
	.needed {
		color: #C0FFCB;
	}
</style>
