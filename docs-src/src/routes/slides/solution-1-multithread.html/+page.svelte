<script lang="ts">
	import Box         from '$lib/components/Box.svelte';
	import ContentPage from '$lib/templates/ContentPage.svelte';
	import Hint        from '$lib/components/Hint.svelte';
	import Note        from '$lib/components/Note.svelte';
	import WideDiv     from '$lib/components/WideDiv.svelte';

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
				<li>Use "Virtual threads"</li>
				<li>Extract concurrently</li>
				<li>Combine concurrently</li>
			</ul>
			<button class="thumbnail" on:click={toggleCodeExpanded}>
				<img src="../multithreads.png" alt="main()" width="350"/>
				<div style="font-size: small;">main()</div>
			</button>
		</div>
	</div>
</ContentPage>
<Hint text="{hint}" />
<Box expanded={isCodeExpanded} width={952} height={664} onClick={toggleCodeExpanded}>
    <img src="../multithreads.png" alt="main()" width="952px" height="664px"/>
</Box>
<Note>
	<p>The first trick used to make this fast is to use virtual threads to read and process each chunk in parallel.</p>
	<p>Virtual threads are also used to combine the statistic maps.</p>
	<p>After some experiment, I ended up spliting the files into 1024 chunks or 32 times the CPI count.</p>
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
		margin-left: 20px;
		cursor: pointer;
		border-radius: 5px;
		border: 2px solid #C0F1FF;
	}
	.thumbnail:active {
		transform: translate(2px, 2px);
		box-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
	}
</style>
