<script lang="ts">
	import Box from '$lib/components/Box.svelte';
	import Hint from '$lib/components/Hint.svelte';
	import NavigationBar from '$lib/components/NavigationBar.svelte';
	import WideDiv from '$lib/components/WideDiv.svelte';
	import ContentPage from '$lib/templates/ContentPage.svelte';
	import { faThList } from '@fortawesome/free-solid-svg-icons';
	import { text } from '@sveltejs/kit';
	import { onMount } from 'svelte';

	let hint = "scroll to pan";

	let isCodeExpanded = false;

	function toggleCodeExpanded() {
		isCodeExpanded = !isCodeExpanded;
	}

	let svgContent = '';
	let items: HTMLElement;

	async function fetchSVG() {
		const response = await fetch('../Solution-Diagram.svg');
		svgContent
				= (await response.text())
				.replaceAll("url('virgil.ttf')",             "url('../virgil.ttf')")
				.replaceAll("width=\"3830.640967790001\"",   "width=\"1973\"")
				.replaceAll("height=\"1048.6091475027065\"", "width=\"540\"");
	}

	function crossOutItem(this: HTMLElement) {
		if (this.classList.contains("accept")) {
			this.style.color = "#C0FFC0";
			let thumbup = document.getElementById("thumbup");
			if (thumbup != null) {
				thumbup.style.visibility = "visible";
			}
		} else {
			this.style.textDecoration = "line-through";
			this.style.color = "#FFC0C0";
		}
	}

	onMount(() => {
		fetchSVG();

		// Select all rect elements inside the svg and add event listeners
		const choices = items.querySelectorAll('li');
		choices.forEach((choice) => {
			choice.addEventListener('click', crossOutItem);
		});
		return () => {
			choices.forEach((choice) => {
				choice.removeEventListener('click', crossOutItem);
			});
		};
	});
</script>

<ContentPage title="Solution (2): Memory Mapped File">
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
			Choices
			<ul bind:this={items}  style="list-style-type: circle;">
				<li>Read the file to a string</li>
				<li>Read the lines to strings</li>
				<li>Read bytes using <code>RandomAccessFile</code></li>
				<li>Direct map to <code>ByteBuffer</code> (native memory)</li>
				<li class="accept">
					Map to <code>ByteBuffer</code> on heap 
					<img id="thumbup" src="../thumbs-up-transparent.png" alt="thumbup" />
				</li>
			</ul>

			<button class="thumbnail" on:click={toggleCodeExpanded}>
				<img src="../memory-mapped-file.png" alt="main()" width="350"/>
			</button>
		</div>
	</div>
</ContentPage>
<NavigationBar
	prevLink="./solution-1-multithread.html"
	nextLink="./solution-3-string-conversion.html"
/>
<Box expanded={isCodeExpanded} width={932} height={551} onClick={toggleCodeExpanded}>
    <img src="../memory-mapped-file.png" alt="main()" width="932px" height="551px"/>
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
		margin-left: 30px;
		cursor: pointer;
		border-radius: 5px;
		border: 2px solid #C0F1FF;
	}
	.thumbnail:active {
		transform: translate(2px, 2px);
		box-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
	}
	li {
		cursor: pointer;
	}
	li:hover {
		color: #C0FFCB;
	}
	#thumbup {
		width: 32px;
		visibility: hidden;
		vertical-align: middle;
	}
</style>
