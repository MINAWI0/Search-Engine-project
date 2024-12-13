# PDF Search Engine

A Spring Boot application that provides PDF document indexing and searching capabilities using TF-IDF scoring and cosine similarity.

## Overview

This application allows users to:
- Index PDF documents from a specified directory
- Extract text content and generate thumbnails from PDFs
- Search through indexed documents using vector space model
- View search results with document previews

## Project Structure

### Controllers

#### IndexController
- Handles document indexing operations
- Endpoint: `/api/index` (POST)
- Indexes all documents in the repository

#### SearchController
- Manages search operations and result display
- Endpoints:
  - `/` : Shows search form
  - `/search` : Processes search queries with pagination

### Services

#### IndexService
- Implements document indexing logic
- Calculates TF-IDF scores
- Manages term frequencies and inverse document frequencies
- Updates document vectors

#### SearchService
- Implements search functionality
- Creates query vectors
- Calculates cosine similarity between documents and queries
- Returns ranked search results

### Utilities

#### DocumentUtil
- Processes PDF files
- Extracts text content
- Generates thumbnails
- Saves documents to the repository

#### TikaExtractionUtils
- Handles text extraction from PDFs using Apache Tika
- Cleans extracted text
- Removes stop words
- Uses English analyzer for text processing

### Models

#### Document
- Represents a PDF document
- Properties:
  - id: Long
  - title: String
  - content: String
  - docLength: Integer
  - filePath: String
  - thumbnailPath: String

#### Term
- Represents unique terms in the corpus
- Properties:
  - id: Long
  - term: String
  - idfScore: Double
  - documentCount: Integer

#### DocumentTerm
- Represents relationship between documents and terms
- Properties:
  - id: Long
  - document: Document
  - term: Term
  - tfScore: Double
  - idfScore: Double
  - tfIdfScore: Double

## Technical Stack

- Java Spring Boot
- JPA/Hibernate
- Apache PDFBox
- Apache Tika
- Apache Lucene (English Analyzer)
- Lombok
- MySQL ( Docker )

## Features

1. **PDF Processing**
   - Text extraction
   - Thumbnail generation
   - Metadata handling

2. **Text Processing**
   - Stop word removal
   - Text normalization
   - Term frequency calculation

3. **Search Implementation**
   - TF-IDF scoring
   - Vector space model
   - Cosine similarity ranking

4. **Web Interface**
   - Search form
   - Results display
   - Document preview

## Setup Requirements

1. Java 17 or higher
2. Maven
3. MySQL database
4. Sufficient storage for PDF documents and thumbnails

## Installation

1. Clone the repository
2. Configure database properties in `application.properties`
3. Create necessary directories for document storage
4. Run `mvn clean install`
5. Start the application using `/Search-Engine-project$ java -Xmx2g -Xms512m -Dpdfbox.rendering.tilingpaint.maxedge=512 -jar target/EnginSearchv4-0.0.1-SNAPSHOT.jar --THUMBNAIL_PATH=/home/ubuntu/engine/ --DOWNLOAD_PATH=/home/ubuntu/engine/`

## Usage

1. Place PDF documents in the configured directory
2. Call the index endpoint to process documents
3. Use the search interface to find documents
4. View results with thumbnails and download options

## Note

Ensure proper file permissions and storage capacity for PDF processing and thumbnail generation. The system requires read/write access to the configured directories.
