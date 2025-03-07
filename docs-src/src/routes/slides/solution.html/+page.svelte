<script lang="ts">
	import Hint from '$lib/components/Hint.svelte';
	import NavigationBar from '$lib/components/NavigationBar.svelte';
	import Note from '$lib/components/Note.svelte';
	import WideDiv from '$lib/components/WideDiv.svelte';
	import ContentPage from '$lib/templates/ContentPage.svelte';
	import { onMount } from 'svelte';

	let hint = "scroll to pan";
	
	function closeBlind(this: HTMLElement) {
		this.style.display = "none";
	}
	function hoverBlind() {
		hint = "click to reveal";
	}
	function exitBlind() {
		hint = "scroll to pan";
	}

	let svgContent = '';
	let blinds: SVGSVGElement;

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

		// Select all rect elements inside the svg and add event listeners
		const rects = blinds.querySelectorAll('rect');
		rects.forEach((rect) => {
			rect.addEventListener('click',     closeBlind);
			rect.addEventListener('mouseover', hoverBlind);
			rect.addEventListener('mouseout',  exitBlind);
		});
		return () => {
			rects.forEach((rect) => {
				rect.removeEventListener('click',     closeBlind);
				rect.removeEventListener('mouseover', hoverBlind);
				rect.removeEventListener('mouseout',  exitBlind);
			});
		};
	});
</script>

<ContentPage title="Solution">
	<WideDiv
		outerWidth="1190"
		innerWidth="2000"
		height="500"
		startScrollPosition={-500}
		style="border: 2px #C0F1FF solid; border-radius: 2px; height: 550px; background-color: #181818;">

		<div id="diagram">
			{#if svgContent}
			{@html svgContent}
			{/if}
			<!-- The absolute dimension here is to make sure it is the same size the diagram image. -->
			<svg id="blinds" bind:this={blinds} xmlns="http://www.w3.org/2000/svg" version="1.1" viewBox="0 0 3830.640967790001 1048.6091475027065" width="3830.640967790001" height="1048.6091475027065">
				<rect style="fill:#080808;fill-opacity:0.9;stroke:none" x="1720" y="5" width="257" height="540" rx="10" ry="10" />
				<rect style="fill:#080808;fill-opacity:0.9;stroke:none" x="1462" y="5" width="515" height="540" rx="10" ry="10" />
				<rect style="fill:#080808;fill-opacity:0.9;stroke:none" x="740" y="5" width="1237" height="540" rx="10" ry="10" />
				<rect style="fill:#080808;fill-opacity:0.9;stroke:none" x="310" y="5" width="1667" height="540" rx="10" ry="10" />
				<rect style="fill:#080808;fill-opacity:0.9;stroke:none" x="117" y="5" width="1860" height="540" rx="10" ry="10" />
			</svg>
		</div>
	</WideDiv>
</ContentPage>
<Hint text="{hint}" />
<Note>
	<p>Here is the high-level view of the solution. I will go over the details in the incoming slides.</p>
	<p>
		Starting from the file, we read them into chunks.
		For each chunk, the data is then extracted into a map -- a hashmap to be specific.
	</p>
	<p>
		The station name is used as the entry key.
		The entry value contains: min, max, sum and count.
	</p>
	<p>
		After that, every pair of map is combined into the larger one. 
		This continues until we get one final map.
	</p>
	<p>Next, the map is sorted by its key and finally print it out.</p>
	<p>Quite straightforward. </p>
</Note>

<style>
	#diagram {
		position: relative;
	}

	#blinds {
		position: absolute;
		top: 0px;
		left: 0px;
	}
	#blinds rect:hover {
		fill-opacity:0.75 !important;
		fill:#0f0f08 !important;
	}
</style>
