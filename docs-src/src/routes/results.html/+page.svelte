<script lang="ts">
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import ContentPage from "$lib/templates/ContentPage.svelte";
    import JavaCode from "$lib/components/JavaCode.svelte";
	import Box from "$lib/components/Box.svelte";
  
    // @ts-ignore
    export let data;

	let isI9Expanded   = false;
	let isM6Expanded   = false;
	let isCodeExpanded = false;

	function toggleI9Expand() {
		isI9Expanded = !isI9Expanded;
	}
	function toggleM6Expand() {
		isM6Expanded = !isM6Expanded;
	}
	function toggleCodeExpand() {
		isCodeExpanded = !isCodeExpanded;
	}
</script>

<style>
	b {
		color: burlywood;
	}
	.figure {
		height: 350px;
		margin: auto;
		padding: auto;
		display: block;
		border-radius: 5px;
	}
	.text {
		padding: 10px;
		margin: auto;
		border-collapse: collapse;
	}
	.text td {
		padding-left: 20px;
		margin-left: 20px;
		vertical-align: top;
	}
	.text .left,
	.text .right {
		width: 50%; /* Adjust width as needed */
		padding: 0 10px; /* Add some padding if needed */
	}
	.text div {
		text-align: center;
	}
	.code,
	.result {
		width: 500px;
		cursor: pointer;
		margin: auto;
		margin-top: 0.5em;
	}
	.code:hover,
	.result:hover {
		background-color: #11632c;
		border: 1px solid #f9f9f9;
		border-radius: 3px;
	}
</style>

<Box expanded={isI9Expanded} width={634} height={371} onClick={toggleI9Expand}>
    <img class="result-img" src="i9-Results.png" alt="i9 Results" width="634px" height="371px"/>
</Box>
<Box expanded={isM6Expanded} width={634} height={371} onClick={toggleM6Expand}>
    <img class="result-img" src="m6-Results.png" alt="m6 Results" width="634px" height="371px"/>
</Box>
<Box expanded={isCodeExpanded} width={1000} height={600} linkText="View on GitHub" linkUrl="https://github.com/NawaMan/OneBRC/blob/main/src/onebrc/CalculateAverage_nawaman.java">
	<JavaCode javaCode={data.text} revealLines={[[39, [371, 372, [375, [380, [1]]]]]]} width="1000px" height="600px" />
</Box>

<ContentPage title="Results">
	<img class="figure" src="smug-daniel-craig.gif" alt="So pround" />
	<table class="text">
		<tr>
			<td class="left">
				<div class="result r-i9" on:click={toggleI9Expand} on:keydown={toggleI9Expand} role="button" tabindex="-1">
					<div>i9 13Gen (32 vCPU) - 128GB</div>
					<div><b>2.1507</b> seconds</div>
				</div>
			</td>
			<td class="right">
				<div class="result r-m6a" on:click={toggleM6Expand} on:keydown={toggleM6Expand} role="button" tabindex="-1">
					<div>m6a.8xlarge (32 vCPU) - 128GB</div>
					<div><b>3.215</b> seconds</div>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="code" on:click={toggleCodeExpand} on:keydown={toggleCodeExpand} role="button" tabindex="-1">
					<div>No "Unsafe" - No GraalVM</div>
					<div>Readable Code!</div>
				</div>
			</td>
		</tr>
	</table>
</ContentPage>
<NavigationBar
	prevLink="/overview.html"
	nextLink="/challenge.html"
/>