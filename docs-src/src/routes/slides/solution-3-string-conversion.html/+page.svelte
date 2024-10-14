<script lang="ts">
	import ContentPage from '$lib/templates/ContentPage.svelte';
	import Hint        from '$lib/components/Hint.svelte';
	import Note        from '$lib/components/Note.svelte';
	import WideDiv     from '$lib/components/WideDiv.svelte';

	import { onMount } from 'svelte';

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

<ContentPage title="Solution (3): Delay String Conversion">
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
				<li>Read from file as part of a chunk</li>
				<li>Recorgnized and extracted as name</li>
				<li>Looked up in the map as a key</li>
				<li> ... for both to get and put</li>
				<li> and for both the initial extraction and later combination.</li>
				<li class="needed">Used to sort the entries as UTF-8.</li>
				<li class="needed">Print out</li>
			</ul>
			Only the last two steps that the name <b>must</b> be a string.<br />
		</div>
	</div>
</ContentPage>
<Hint text="scroll to pan" />
<Note>
	<p>
		We touch a little bit in the previous trick that
		... creating a string is expensive as a string needs an isolated array of bytes to store its characters.
		Let's review the journey of the station name which is the string used for the whole process.
	</p>
	<p>
	READ THE SLIDE
	</p>
	<p>
		Therefore, we can delay the conversion of the station name to a string until we really need at the sorting step. ";
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
	#side-content {
		padding-top: 20px;
		padding-left: 30px;
		padding-right: 30px;
	}
	#side-content ul {
		padding-top: 0px;
		padding-bottom: 20px;
		padding-left: 20px;
		margin: 0px;
	}
	.needed {
		color: #C0FFCB;
	}
</style>
