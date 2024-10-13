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

<ContentPage title="Solution (7): Name Normalization">
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
		<div class="side-content">
			To speed up the process of combining,
			if can assign a unique ID for each name.
			So the subsequent comparison can skip the array equals step.

			<ul>
				<li>A new name detect for each chunk.</li>
				<li>The name is added to the queue.</li>
				<li>Unique ID us obtained or generated.</li>
				<li>The ID is assigned back to the name.</li>
			</ul>
		</div>
		<div id="thumbnails">
			<div class="thumbnail thumbnail1" >
				<button on:click={toggleCodeOneExpanded}>
					<img src="../add-name-to-queue.png" alt="main()" width="164"/>
					<div style="font-size: small;">Add name to queue</div>
				</button>
			</div>
			<div class="thumbnail thumbnail2" >
				<button on:click={toggleCodeTwoExpanded}>
					<img src="../assign-name-id.png" alt="main()" width="155"/>
					<div style="font-size: small;">Assign name ID</div>
				</button>
			</div>
			<div class="thumbnail thumbnail3" >
				<button on:click={toggleCodeThreeExpanded}>
					<img src="../id-in-name-equals.png" alt="main()" width="124"/>
					<div style="font-size: small;">ID in name <code>equals()</code></div>
				</button>
			</div>
		</div>
	</div>
</ContentPage>
<Box expanded={isCodeOneExpanded} width={915} height={399} onClick={toggleCodeOneExpanded}>
	<img src="../add-name-to-queue.png" alt="main()" width="915px" height="399px"/>
</Box>
<Box expanded={isCodeTwoExpanded} width={859} height={548} onClick={toggleCodeTwoExpanded} scrollable={true}>
	<img src="../assign-name-id.png" alt="main()" width="859px" height="548px"/>
</Box>
<Box expanded={isCodeThreeExpanded} width={689} height={681} onClick={toggleCodeThreeExpanded} scrollable={true}>
	<img src="../id-in-name-equals.png" alt="main()" width="689px" height="681px"/>
</Box>
<NavigationBar
	prevLink="./solution-6-vectorize-compare.html"
	nextLink="./conclusion.html"
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
	.side-content {
		padding-top: 10px;
		padding-left: 20px;
		padding-right: 20px;
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
