<script lang="ts">
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import ImgBox from '$lib/components/ImgBox.svelte';
	import ContentPage from "$lib/templates/ContentPage.svelte";

	let isI9Expanded = false;
	let isM6Expanded = false;

	function toggleI9Expand() {
		isI9Expanded = !isI9Expanded;
	}
	function toggleM6Expand() {
		isM6Expanded = !isM6Expanded;
	}
</script>

<style>
	b {
		color: burlywood;
	}
	img {
		height: 350px;
		margin: auto;
		padding: auto;
		display: block;
		border-radius: 5px;
	}
	.text {
		width: 80%;
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
	.result:hover {
		background-color: #11632c;
		border: 1px solid #f9f9f9;
		border-radius: 3px;
	}
</style>

<ImgBox
	expanded={isI9Expanded}
	imgSrc="i9-Results.png"
	altText="i9 Results"
	width={634}
	height={371}
	onClick={toggleI9Expand}
/>
<ImgBox
	expanded={isM6Expanded}
	imgSrc="m6-Results.png"
	altText="m6 Results"
	width={634}
	height={371}
	onClick={toggleM6Expand}
/>
<ContentPage title="Results">
	<img src="smug-daniel-craig.gif" alt="So pround" />
	<table class="text">
		<tr>
			<td class="left">
				<div>&nbsp;</div>
				<div class="result r-i9" on:click={toggleI9Expand} on:keydown={toggleI9Expand} role="button" tabindex="-1">
					<div>i9 13Gen (32 vCPU) - 128GB</div>
					<div><b>2.1507</b> seconds</div>
				</div>
			</td>
			<td class="right">
				<div>&nbsp;</div>
				<div class="result r-m6a" on:click={toggleM6Expand} on:keydown={toggleM6Expand} role="button" tabindex="-1">
					<div>m6a.8xlarge (32 vCPU) - 128GB</div>
					<div><b>3.215</b> seconds</div>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div>&nbsp;</div>
				<div>No "Unsafe" - No GraalVM</div>
				<div>Readable Code</div>
			</td>
		</tr>
	</table>
</ContentPage>
<NavigationBar
	prevLink="/overview.html"
	nextLink="/challenge.html"
/>