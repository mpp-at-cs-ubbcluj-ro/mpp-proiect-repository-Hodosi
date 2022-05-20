using System;
using System.Configuration;
using System.Dynamic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace CSharp_RestClient
{
    internal class Program
    {
        static HttpClient client = new HttpClient();
        
        public static void Main(string[] args)
        {
            Console.WriteLine("Hey hey");
            RunAsync().Wait();
        }

        static async Task RunAsync()
        {
            client.BaseAddress = new Uri("http://localhost:8088/competition/tests/");
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

            Test result = await getById("http://localhost:8088/competition/tests/" + 1.ToString());
            Console.WriteLine(result);
            
            
            Test[] resultSet = await getAll("http://localhost:8088/competition/tests/");
            foreach (Test test in resultSet)
            {
                Console.WriteLine(test);
            }

            TestType type = new TestType();
            type.id = 77;
            TestAgeCategory category = new TestAgeCategory();
            category.id = 77;
            Test newTest = new Test(type, category);

            result = await create("http://localhost:8088/competition/tests/", newTest);
            Console.WriteLine(result);

            type.id = 88;
            category.id = 88;
            Test upTest = new Test(type, category);
            upTest.id = 22;
            
            result = await update("http://localhost:8088/competition/tests/" + upTest.id, upTest);
            Console.WriteLine(result);

            await delete("http://localhost:8088/competition/tests/" + 23);
            
            Console.ReadLine();
        }

        static async Task<Test> getById(string path)
        {
            Test test = null;
            HttpResponseMessage response = await client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                test = await response.Content.ReadAsAsync<Test>();
            }

            return test;
        }

        static async Task<Test[]> getAll(string path)
        {
            Test[] tests = null;
            HttpResponseMessage response = await client.GetAsync(path);
            if (response.IsSuccessStatusCode)
            {
                tests = await response.Content.ReadAsAsync<Test[]>();
            }

            return tests;
        }

        static async Task<Test> create(string path, Test newTest)
        {
            Test created = null;
            HttpContent content = new StringContent(JsonConvert.SerializeObject(newTest), Encoding.UTF8);
            content.Headers.ContentType = new MediaTypeWithQualityHeaderValue("application/json");
            HttpResponseMessage response = await client.PostAsync(path, content);
            if (response.IsSuccessStatusCode)
            {
                created = await response.Content.ReadAsAsync<Test>();
            }

            return created;

        }

        static async Task<Test> update(string path,Test upTest)
        {
            HttpContent content = new StringContent(JsonConvert.SerializeObject(upTest), Encoding.UTF8);
            content.Headers.ContentType = new MediaTypeWithQualityHeaderValue("application/json");
            HttpResponseMessage response = await client.PutAsync(path, content);
            if (response.IsSuccessStatusCode)
            {
                return upTest;
            }
            
            return null;
        }

        static async Task<Test> delete(string path)
        {
            Test test = null;
            HttpResponseMessage response = await client.DeleteAsync(path);
            return test;
        }
    }

    public class TestType
    {
        public int id { get; set; }
        public string type { get; set; }

        public TestType()
        {

        }

        public TestType(string type)
        {
            this.type = type;
        }
    }

    public class TestAgeCategory
    {
        public int id { get; set; }
        public int minAge { get; set; }
        public int maxAge { get; set; }

        public TestAgeCategory()
        {

        }

        public TestAgeCategory(int minAge, int maxAge)
        {
            this.minAge = minAge;
            this.maxAge = maxAge;
        }
    }
    
    public class Test
    {
        public int id { get; set; }
        public TestType type { get; set; }
        public TestAgeCategory category { get; set; }

        public Test()
        {
            
        }

        public Test(TestType type, TestAgeCategory category)
        {
            this.type = type;
            this.category = category;
        }

        public override string ToString()
        {
            if (type == null || category == null)
            {
                return string.Format("Test id={0}, type id=null or/and category id=null", id);
            }
            return string.Format("Test id={0}, type id={1}, category id={2}", id, type.id, category.id);
        }
    }
}