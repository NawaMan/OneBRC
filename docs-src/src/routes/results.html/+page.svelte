<script>
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import YouTube from "$lib/components/YouTube.svelte";
	import ContentPage from "$lib/templates/ContentPage.svelte";
</script>

<style>
	b {
		color: burlywood;
	}
	.text {
		width: 100%;
	}
	img {
		height: 400px;
		margin: auto;
		padding: auto;
		display: block;
	}
</style>

<ContentPage title="Results">
	<img src="smug-daniel-craig.gif" alt="So pround" />
	<table class="text">
		<tr>
			<td class="left">
				<ul>
					<li><b>Some</b> point</li>
					<li><b>Another</b> point</li>
				</ul>
			</td>
			<td class="right">
				<ul>
					<li><b>More</b> point</li>
					<li><b>Even more</b> point</li>
				</ul>
			</td>
		</tr>
	</table>
</ContentPage>
<NavigationBar
	prevLink="/overview.html"
	nextLink="/challenge.html"
/>