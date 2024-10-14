<script lang="ts">
	import ContentPage     from '$lib/templates/ContentPage.svelte';
	import Hint            from '$lib/components/Hint.svelte';
	import Note            from '$lib/components/Note.svelte';
	import SolutionCodeBox from '$lib/components/SolutionCodeBox.svelte';
	import WideDiv         from '$lib/components/WideDiv.svelte';

	import { onMount } from 'svelte';

  	// @ts-ignore
  	export let data;

	$: revealedLines = [1];

	let isCodeExpanded = false;

  	// @ts-ignore
	function toggleCodeExpanded(revealedLine) {
		return function() {
			revealedLines  = [revealedLine];
			isCodeExpanded = !isCodeExpanded;
		}
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
				<button on:click={toggleCodeExpanded(338)}>
					<img src="../add-name-to-queue.png" alt="main()" width="164"/>
					<div style="font-size: small;">Add name to queue</div>
				</button>
			</div>
			<div class="thumbnail thumbnail2" >
				<button on:click={toggleCodeExpanded(134)}>
					<img src="../assign-name-id.png" alt="main()" width="155"/>
					<div style="font-size: small;">Assign name ID</div>
				</button>
			</div>
			<div class="thumbnail thumbnail3" >
				<button on:click={toggleCodeExpanded(95)}>
					<img src="../id-in-name-equals.png" alt="main()" width="124"/>
					<div style="font-size: small;">ID in name <code>equals()</code></div>
				</button>
			</div>
		</div>
	</div>
</ContentPage>
<Hint text="scroll to pan" />
<SolutionCodeBox expanded={isCodeExpanded} javaCode={data.javaCode} {revealedLines}/>
<Note>
	<p>The last trick helps improve the comination speed.</p>
	<p>
		We already discussed that equal check is always need when get and put data.
		We can speed this up by assigning a unique number as an ID to each name.
	</p>
	<p>The subsequence equal check will just need to compare the ID.</p>
	<p>CLICK ON 'Add name to queue': When a new station name is detected for each chunk the name is added to the name queue.</p>
	<p>CLICK ON 'Assign name ID': The name queue will try to add to the name ID map which returns the new ID if none is ready there or the existing ID if the name is ready there.</p>
	<p>
		CLICK ON 'ID in name equals()':
		Lastly, in the StationName 'equals()', if IDs are present, the ID is then used to do equal check. 
		And this will happen for each key during the combine. 
		Since there are 1024 chunks, saving the key 'equal check' time from byte-array equals to int compare save a lot of the overall time.
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
