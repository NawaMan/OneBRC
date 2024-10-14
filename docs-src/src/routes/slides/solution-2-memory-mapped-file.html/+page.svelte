<script lang="ts">
	import ContentPage     from '$lib/templates/ContentPage.svelte';
	import Hint            from '$lib/components/Hint.svelte';
	import Note            from '$lib/components/Note.svelte';
	import SolutionCodeBox from '$lib/components/SolutionCodeBox.svelte';
	import WideDiv         from '$lib/components/WideDiv.svelte';

	import { onMount } from 'svelte';
  
	// @ts-ignore
	export let data;

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
			Alternatives
			<ul bind:this={items}  style="list-style-type: circle;">
				<li>Read the file to a string</li>
				<li>Read the lines to strings</li>
				<li>Read bytes using <code>RandomAccessFile</code></li>
				<li>Direct map to native memory (outside of JVM)</li>
				<li class="accept">
					Map to memory in the heap 
					<img id="thumbup" src="../thumbs-up-transparent.png" alt="thumbup" />
				</li>
			</ul>

			<button class="thumbnail" on:click={toggleCodeExpanded}>
				<img src="../memory-mapped-file.png" alt="main()" width="350"/>
				<div style="font-size: small;">StatisticExtractor.create()</div>
			</button>
		</div>
	</div>
</ContentPage>
<Hint text="scroll to pan" />
<SolutionCodeBox expanded={isCodeExpanded} javaCode={data.javaCode} revealedLines={[278]}/>
<Note>
	<p>
		The next trick is due the large size of the file, the read speed is one of the one biggest factor of the overall performce.
		Different reading methods not only have different performance characteristics,
		it also returns different data format with different performance chracteristic of itsown.
		Another word, we need to consider both reading and processing speed. of each alternative.
	</p>
	<p>
		Before diving into the alternatives, we need to keep in mind that the foundamental data structure is always array of bytes.
		A string is backed by an array of bytes and since bytes are multable every new string will has itsown array of bytes.
	</p>
	<ol>
		<li>
			With that in mind, the first alternative is read the whole file or the whole chunk into a string
			but that means double memory and copy time to create array of bytes for each station name as a string.
		</li>
		<li>Next alternative is to do it line by line but that suffer similar problem.</li>
		<li>Next, we can use RandomAccessFile --- but its position index is in <code>int</code> which is not big enough for 13GB file.</li>
		<li>The next alternative is to use memory mapped files which can be done in two ways:
			<ol>
				<li>direct mapping native memory outside of JVM,  and</li>
				<li> mapping to heap.</li>
			</ol>
			Direct mapping to native memory is faster to read but the result data structure is slow to process -- in the way we need to, anyway.
			So the fastest choice ended be mapping file to heap.
		</li>
	</ol>
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
	#side li {
		cursor: pointer;
	}
	#side li:hover {
		color: #C0FFCB;
	}
	#thumbup {
		width: 32px;
		visibility: hidden;
		vertical-align: middle;
	}
</style>
