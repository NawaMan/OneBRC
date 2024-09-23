<script lang="ts">
	import Box from "$lib/components/Box.svelte";
	import ContentPage from '$lib/templates/ContentPage.svelte';
    import JavaCode from "$lib/components/JavaCode.svelte";
	import NavigationBar from '$lib/components/NavigationBar.svelte';
	import WideDiv from '$lib/components/WideDiv.svelte';
	import { onMount } from 'svelte';
	import classNames from 'classnames';
  
	// @ts-ignore
	export let data;
  
	let linkCode = true;
	
	$: classes = classNames({
		'linkCode': linkCode
	});

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
	});
</script>

<ContentPage title="Chunk">
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
			<!-- The absolute dimension here is to make sure it is the same size the diagram image. -->
			<svg id="blinds" bind:this={blinds} xmlns="http://www.w3.org/2000/svg" version="1.1" viewBox="0 0 3830.640967790001 1048.6091475027065" width="3830.640967790001" height="1048.6091475027065">
				<rect style="fill:#080808;fill-opacity:0.9;stroke:none" x="195" y="5" width="1667" height="540" rx="10" ry="10" />
			</svg>
		</div>
	</WideDiv>
</ContentPage>

<div style="position: absolute; width: 1000px; height: 600px; left: 250px; top: 75px">
	<Box expanded={true} width={1000} height={600} linkText="View on GitHub" shadowOpacity={0} linkUrl="https://github.com/NawaMan/OneBRC/blob/main/src/onebrc/CalculateAverage_nawaman.java">
		<JavaCode javaCode={data.javaCode} revealLines={[39, [281, [350, [431]]]]} cursorLine={416} width="1000px" height="600px" />
	</Box>
</div>

<NavigationBar prevLink="./solution.html" />

<style>
	#diagram {
		position: relative;
	}

	#blinds {
		position: absolute;
		top: 0px;
		left: 0px;
	}
</style>
