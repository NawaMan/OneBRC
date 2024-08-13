<script>
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import ContentPage from "$lib/templates/ContentPage.svelte";
</script>

<style>
	ul {
		margin: 0px;
	}
	ul li ul {
		font-size: 0.9em;
	}
	.left,
	.right {
		margin: 0px;
		padding: 0px;
		display: inline-block;
		vertical-align: top;
		text-align: left;
	}
	.left {
		width: 58%;
	}
	.right {
		padding: 1em;
		width: 35%;
	}
	.right table,
	.right table td {
		border-collapse: collapse;
		border: 2px solid aliceblue;
		padding-left: 0.5em;
		padding-right: 0.5em;
	}
	.right table td:first-child {
		text-align: center;
	}
	.blur {
		color: transparent; /* Make the text itself invisible */
		text-shadow: 0 0 8px rgba(240, 248, 255, 0.8); /* Apply a shadow to create the blur */
	}
</style>

<NavigationBar
	prevLink="/"
/>
<ContentPage
	title="One Billion Row Challenge"
	subtitle="The Challenge">
	<div class="left">
	<ul>
		<li>Read a CSV file with 1 billion rows</li>
		<li>Each line weather station name and temperature
			<ul>
				<li>Each row is delimetered by a new line '\n'</li>
				<li>Station name and temperature is seprated by ';'</li>
				<li>Each station name is UTF-8 (100 bytes or shorter)</li>
				<li>The temperature is between -99.9 to 99.9 <br/>(1-2 digit before decinal and 1 digit after decinal)</li>
			</ul>
		</li>
		<li>Calculate min, max and average temperatures <br/>for each station.</li>
		<li>Print the results</li>
		<li>No external dependency</li>
		<li>Source code in one file</li>
		<li>Benchmark on 32 cores CPU and 128 GB RAM</li>
	</ul>
	</div>
	<div class="right">
		<table>
			<tr><td>1</td><td>Ottawa;14.2</td></tr>
			<tr><td>2</td><td>Bangkok;35.0</td></tr>
			<tr><td>3</td><td>Sydney;2.5</td></tr>
			<tr><td>4</td><td>New Delhi;-0.2</td></tr>
			<tr><td>5</td><td>Abéché;89.3</td></tr>
			<tr class="blur"><td>455</td><td>New York;20.7</td></tr>
			<tr class="blur"><td>15,466</td><td>Mars;-24.7</td></tr>
			<tr class="blur"><td>1,546,536</td><td>Venus;84.5</td></tr>
			<tr><td>1,000,000,000</td><td>Budapest;14.9</td></tr>
		</table>
	</div>
</ContentPage>