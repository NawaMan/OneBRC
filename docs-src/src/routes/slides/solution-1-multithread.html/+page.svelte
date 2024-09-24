<script lang="ts">
	import Hint from '$lib/components/Hint.svelte';
	import NavigationBar from '$lib/components/NavigationBar.svelte';
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

<ContentPage title="Solution (1): Mutiple-thread processing">
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
		<div class="blind">
		</div>
		<div id="side">
			Hello
		</div>
	</WideDiv>
</ContentPage>
<NavigationBar prevLink="./solution.html" />

<style>
	#diagram {
		position: relative;
	}

	#side,
	.blind {
		position: absolute;
		top: 0px;
		left: 0px;
		background-color: #181818;
	}
	.blind {
		left: 570px;
		top: 480px;
		width: 190px;
		height: 80px;
	}
	#side {
		left:   750px;
		top:     15px;
		width:  432px;
		height: 520px;
		border-radius: 10px;
		box-shadow: 0 0 20px 20px rgba(0, 0, 0, 0.8);
	}
</style>
