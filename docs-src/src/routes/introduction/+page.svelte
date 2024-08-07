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
		<li>Sum the 3rd column.</li>
		<li>Print the sum.</li>
	</ul>
</ContentPage>