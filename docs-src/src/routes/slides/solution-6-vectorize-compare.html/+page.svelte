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
	let isCodeThreeExpanded = false;

	function toggleCodeOneExpanded() {
		isCodeOneExpanded = !isCodeOneExpanded;
	}
	function toggleCodeTwoExpanded() {
		isCodeTwoExpanded = !isCodeTwoExpanded;
	}
	function toggleCodeThreeExpanded() {
		isCodeThreeExpanded = !isCodeThreeExpanded;
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

<ContentPage title="Solution (6): Vectorize Comparison">
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
				<li>Name is used as map key.</li>
				<li>Map use <code>hash</code> to short list match.</li>
				<li>But map will confirm with <code>equals</code>.</li>
				<li>Thus, name <code>equals</code> must be fast.</li>
				<li>To do we use vectorization.</li>
				<li>Fortunately that was built in!</li>
			</ul>
			<div id="thumbnails">
				<div class="thumbnail thumbnail1" >
					<button on:click={toggleCodeOneExpanded}>
						<img src="../name-array-equals.png" alt="main()" width="185.63"/>
						<div style="font-size: small;">StationName.equals()</div>
					</button>
				</div>
				<div class="thumbnail thumbnail2" >
					<button on:click={toggleCodeTwoExpanded}>
						<img src="../arrays-equals.png" alt="main()" width="128.43"/>
						<div style="font-size: small;">Arrays.equals()</div>
					</button>
				</div>
				<div class="thumbnail thumbnail3" >
					<button on:click={toggleCodeThreeExpanded}>
						<img src="../arraysupport-mismatch.png" alt="main()" width="175.18"/>
						<div style="font-size: small;">ArraySupports.mismatch()</div>
					</button>
				</div>
			</div>
		</div>
	</div>
</ContentPage>
<NavigationBar
	prevLink="./solution-5-value-as-integer.html"
	nextLink="./solution-7-name-normalization.html"
/>
<Box expanded={isCodeOneExpanded} width={675} height={386} onClick={toggleCodeOneExpanded} left="400px">
	<img src="../name-array-equals.png" alt="StationName.equals()" width="675px" height="386px"/>
</Box>
<Box expanded={isCodeTwoExpanded} width={656} height={279} onClick={toggleCodeTwoExpanded} scrollable={true} left="400px">
	<img src="../arrays-equals.png" alt="Arrays.equals()" width="656px" height="279px"/>
</Box>
<Box expanded={isCodeThreeExpanded} width={616} height={539} onClick={toggleCodeThreeExpanded} scrollable={true} left="400px">
	<img src="../arraysupport-mismatch.png" alt="ArraySupports.mismatch()" width="616px" height="539px"/>
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

	#thumbnails {
		padding-left: 20px;
		padding-right: 20px;
		display: grid;
		grid-template-columns: 1fr 1fr;
		grid-template-rows: auto auto;
		gap: 5px;
	}
	.thumbnail {
		padding: 0px;
		border: none;
		border-radius: 5px;
		cursor: pointer;
		background-color: transparent;
	}
	.thumbnail:active {
		transform: translate(2px, 2px);
		box-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
	}
	.thumbnail1 {
		grid-column: 1;
		grid-row: 1;
	}

	.thumbnail2 {
		grid-column: 1;
		grid-row: 2;
	}

	.thumbnail3 {
		grid-column: 2;
		grid-row: 1 / 3;
	}

	.thumbnail {
		padding: 2px;
		display: flex;
		align-items: center;
		justify-content: center;
	}
</style>
