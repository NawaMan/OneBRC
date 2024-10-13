

<script lang="ts">
	import Box from '$lib/components/Box.svelte';
	import Hint from '$lib/components/Hint.svelte';
	import NavigationBar from '$lib/components/NavigationBar.svelte';
	import Note from '$lib/components/Note.svelte';
	import WideDiv from '$lib/components/WideDiv.svelte';
	import ContentPage from '$lib/templates/ContentPage.svelte';
	import { onMount } from 'svelte';

	let hint = "scroll to pan";

	let isCodeOneExpanded = false;

	function toggleCodeOneExpanded() {
		isCodeOneExpanded = !isCodeOneExpanded;
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

<ContentPage title="Solution (5): Value as Integer">
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
				<li>Floating point number is not precise for 1 digit decimal.</li>
				<li>Convert string to float is expensive.</li>
				<li>The value can be represent using integer but times 10</li>
				<li>For example, 14.2 can be represent using int 142.</li>
			</ul>

			<div id="thumbnails">
				<button class="thumbnail" on:click={toggleCodeOneExpanded}>
					<img src="../TemperatureBuffer.png" alt="main()" width="272"/>
				</button>
			</div>
		</div>
	</div>
</ContentPage>
<NavigationBar
	prevLink="./solution-4-name-as-byte-array.html"
	nextLink="./solution-6-vectorize-compare.html"
/>
<Box expanded={isCodeOneExpanded} width={660} height={605} onClick={toggleCodeOneExpanded} left="390px" top="390px" shadowOpacity={0.0}>
	<img src="../TemperatureBuffer.png" alt="main()" width="660px" height="605px"/>
</Box>
<Note>
	<p>
		Next we optimize the extraction of the temperature value from the string.
		Instead of extract to float or double, we can represent the value as integer.
	</p>
	<p>
		Well ...
	</p>
</Note>

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
		font-size: 22px;
		background-color: #181818;
		border-radius: 10px;
		box-shadow: 0 0 20px 20px rgba(0, 0, 0, 0.8);
	}
	#thumbnails {
		display: flex;
		justify-content: center;
		padding-top: 0px;
	}
	.thumbnail {
		padding: 0px;
		border: none;
		border-radius: 5px;
		cursor: pointer;
		background-color: transparent;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.thumbnail:active {
		transform: translate(2px, 2px);
		box-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
	}
	.thumbnail img {
		padding-top: 0px;
		padding-left: 10px;
		padding-right: 10px;
		max-height: 100%;
		max-width: 100%;
		object-fit: contain;
	}
</style>
