

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

	function toggleCodeOneExpanded() {
		revealedLines = [172];
		isCodeExpanded = !isCodeExpanded;
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

<ContentPage title="Solution (5): Temperature as Integer (times 10)">
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
		<ul>
			<li>Floating point number is not precise for 1 digit decimal.</li>
			<li>Convert string to float is expensive.</li>
			<li>The value can be represent using integer but times 10</li>
			<li>For example, 14.2 can be represent using int 142.</li>
		</ul>

		<div id="thumbnails">
			<div class="thumbnail" >
				<button on:click={toggleCodeOneExpanded}>
					<img src="../TemperatureBuffer.png" alt="main()" width="272"/>
					<div style="font-size: small;">TemperatureBuffer</div>
				</button>
			</div>
		</div>
	</div>
</ContentPage>
<Hint text="scroll to pan" />
<SolutionCodeBox expanded={isCodeExpanded} javaCode={data.javaCode} {revealedLines}/>
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
	#side ul {
		width: 380px;
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
	.thumbnail button {
		padding: 0px;
	}
	.thumbnail img {
		padding-top: 0px;
		max-height: 100%;
		max-width: 100%;
		object-fit: contain;
	}
</style>
