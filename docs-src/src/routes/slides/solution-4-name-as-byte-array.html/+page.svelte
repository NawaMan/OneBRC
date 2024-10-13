

<script lang="ts">
	import Box         from '$lib/components/Box.svelte';
	import ContentPage from '$lib/templates/ContentPage.svelte';
	import Note        from '$lib/components/Note.svelte';
	import WideDiv     from '$lib/components/WideDiv.svelte';

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

<ContentPage title="Solution (4): Name as Byte Array">
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
				<li>A byte array for read buffer.</li>
				<li>Length and hash are calculated while reading.</li>
				<li>The buffer used for map key check.</li>
				<li>If key exists, reuse the byte array.</li>
				<li>If not exists, the array become the key and create a new read buffer.</li>
			</ul>

			<div id="thumbnails">
				<button class="thumbnail" on:click={toggleCodeTwoExpanded}>
					<img src="../name-equals.png" alt="main()" width="136"/>
				</button>
				<button class="thumbnail" on:click={toggleCodeOneExpanded}>
					<img src="../line-extract.png" alt="main()" width="213"/>
				</button>
			</div>
		</div>
	</div>
</ContentPage>
<Box expanded={isCodeOneExpanded} width={928} height={394} onClick={toggleCodeOneExpanded}>
	<img src="../line-extract.png" alt="main()" width="928px" height="394px"/>
</Box>
<Box expanded={isCodeTwoExpanded} width={698} height={650} onClick={toggleCodeTwoExpanded} scrollable={true} left="380px">
	<img src="../name-equals.png" alt="main()" width="698px" height="1200px"/>
</Box>
<Note>
	So, if we don't use strings, what can we use instead?
	Byte array of course!
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
