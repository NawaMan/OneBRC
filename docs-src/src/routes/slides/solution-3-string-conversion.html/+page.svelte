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
				<li>A byte array for read buffer</li>
				<li>Another byte array for a new string</li>
				<li>The string is thrown away during merge</li>
				<li>Strings only need when sort</li>
				<li>Merging only need hash and equal check</li>
				<li>Byte arrays are comparable</li>
				<li>Reuse byte array when possible</li>
			</ul>

			<div id="thumbnails">
				<button class="thumbnail" on:click={toggleCodeOneExpanded}>
					<img src="../line-extract.png" alt="main()" width="213"/>
				</button>
				<button class="thumbnail" on:click={toggleCodeTwoExpanded}>
					<img src="../name-equals.png" alt="main()" width="136"/>
				</button>
			</div>
		</div>
	</div>
</ContentPage>
<NavigationBar
	prevLink="./solution-2-memory-mapped-file.html"
	nextLink="./solution-4-one-pass-parsing.html"
/>
<Box expanded={isCodeOneExpanded} width={928} height={394} onClick={toggleCodeOneExpanded}>
    <img src="../line-extract.png" alt="main()" width="928px" height="394px"/>
</Box>
<Box expanded={isCodeTwoExpanded} width={698} height={650} onClick={toggleCodeTwoExpanded} scrollable={true}>
    <img src="../name-equals.png" alt="main()" width="698px" height="1200px"/>
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
		font-size: 20px;
		background-color: #181818;
		border-radius: 10px;
		box-shadow: 0 0 20px 20px rgba(0, 0, 0, 0.8);
	}
	.thumbnail {
		margin-left: 5px;
		cursor: pointer;
		border-radius: 5px;
		border: 2px solid #C0F1FF;
	}
	#thumbnails {
		display: flex;
		justify-content: center; /* Center horizontally */
		align-items: center; /* Center vertically if needed */
		flex-wrap: wrap; /* Allow wrapping if there are too many thumbnails */
	}
</style>
