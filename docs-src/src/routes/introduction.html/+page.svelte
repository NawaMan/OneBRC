<script>
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import ContentPage from "$lib/templates/ContentPage.svelte";
</script>

<style>
</style>

<ContentPage
	title="One Billion Row Challenge"
	subtitle="The Challenge">
	<NavigationBar
		prevLink="/"
	/>
	<ul>
		<li>Read a CSV file with 1 billion rows.</li>
		<li>Each line weather station name and temperature</li>
		<li>Calculate min, max and average temperatures <br/>for each station.</li>
		<li>Print the results.</li>
	</ul>
</ContentPage>