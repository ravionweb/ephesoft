/********************************************************************************* 
* Ephesoft is a Intelligent Document Capture and Mailroom Automation program 
* developed by Ephesoft, Inc. Copyright (C) 2010-2012 Ephesoft Inc. 
* 
* This program is free software; you can redistribute it and/or modify it under 
* the terms of the GNU Affero General Public License version 3 as published by the 
* Free Software Foundation with the addition of the following permission added 
* to Section 15 as permitted in Section 7(a): FOR ANY PART OF THE COVERED WORK 
* IN WHICH THE COPYRIGHT IS OWNED BY EPHESOFT, EPHESOFT DISCLAIMS THE WARRANTY 
* OF NON INFRINGEMENT OF THIRD PARTY RIGHTS. 
* 
* This program is distributed in the hope that it will be useful, but WITHOUT 
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
* FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more 
* details. 
* 
* You should have received a copy of the GNU Affero General Public License along with 
* this program; if not, see http://www.gnu.org/licenses or write to the Free 
* Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
* 02110-1301 USA. 
* 
* You can contact Ephesoft, Inc. headquarters at 111 Academy Way, 
* Irvine, CA 92617, USA. or at email address info@ephesoft.com. 
* 
* The interactive user interfaces in modified source and object code versions 
* of this program must display Appropriate Legal Notices, as required under 
* Section 5 of the GNU Affero General Public License version 3. 
* 
* In accordance with Section 7(b) of the GNU Affero General Public License version 3, 
* these Appropriate Legal Notices must retain the display of the "Ephesoft" logo. 
* If the display of the logo is not reasonably feasible for 
* technical reasons, the Appropriate Legal Notices must display the words 
* "Powered by Ephesoft". 
********************************************************************************/ 

package com.ephesoft.dcma.batch.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.ephesoft.dcma.batch.schema.Batch;
import com.ephesoft.dcma.batch.schema.HocrPages;
import com.ephesoft.dcma.batch.schema.Document.DocumentLevelFields;
import com.ephesoft.dcma.batch.schema.HocrPages.HocrPage;
import com.ephesoft.dcma.core.DCMAException;
import com.ephesoft.dcma.core.exception.DCMAApplicationException;
import com.ephesoft.dcma.da.domain.BatchClassModule;
import com.ephesoft.dcma.da.id.BatchClassID;
import com.ephesoft.dcma.da.id.BatchInstanceID;
import com.ephesoft.dcma.util.CustomFileFilter;

/**
 * This is a service to read and write data required by Batch Schema. Api's are present to move, rearrange, delete, create, swap the
 * document and pages from one to each other. On the basis of batch instance id and base folder location will read the batch.xml file
 * and rearrange the attribute of the xml file.
 * 
 * @author Ephesoft
 * @version 1.0
 * @see com.ephesoft.dcma.batch.service.BatchSchemaServiceImpl
 */
public interface BatchSchemaService {

	/**
	 * An API to generate the xml file for all the htmls generated by image process plug-ins.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @throws DCMAException If any error occurs.
	 */
	void htmlToXmlGeneration(String batchInstanceIdentifier) throws DCMAException;

	/**
	 * An API to generate the xml file for all the htmls generated by image process plug-ins.
	 * 
	 * @param batchInstanceIdentifier {@link BatchInstanceID}
	 * @param pluginWorkflow {@link String}
	 * @throws DCMAException
	 */
	void htmlToXmlGeneration(final BatchInstanceID batchInstanceID, final String pluginWorkflow) throws DCMAException;

	/**
	 * An API to get the project files for the given document type and batch class identifier.
	 * 
	 * @param batchClassIdentifier {@link String}
	 * @param documentType {@link String}
	 * @return List<String>
	 * @throws DCMAException
	 */
	List<String> getProjectFilesForDocumentType(String batchClassIdentifier, String documentType) throws DCMAException;

	/**
	 * An API to get the absolute path of the directory. It will create the directory if it is not exits and createDir boolean is true
	 * otherwise not.
	 * 
	 * @param batchClassIdentifier {@link String}
	 * @param directoryName {@link String}
	 * @param createDir {@link boolean}
	 * @return {@link String} absolute path of the directory.
	 */
	String getAbsolutePath(String batchClassIdentifier, String directoryName, boolean createDir);

	/**
	 * An API to return the complete local folder location path.
	 * 
	 * @return {@link String} localFolderLocation
	 */
	String getLocalFolderLocation();

	/**
	 * Script folder name.
	 * 
	 * @return {@link String} script folder name.
	 */
	String getScriptFolderName();

	/**
	 * Cmis plugin mapping folder name.
	 * 
	 * @return {@link String} cmis plugin mapping folder name.
	 */
	String getCmisPluginMappingFolderName();

	/**
	 * An API to return the sample path for search classification.
	 * 
	 * @param batchClassIdentifier {@link String}
	 * @param createDir boolean
	 * @return {@link String} searchClassSamplePath
	 */
	String getSearchClassSamplePath(String batchClassIdentifier, boolean createDir);

	/**
	 * An API to return the index folder for search classification.
	 * 
	 * @param batchClassIdentifier {@link String}
	 * @param createDir boolean
	 * @return {@link String} searchClassSamplePath
	 */
	String getSearchClassIndexFolder(String batchClassIdentifier, boolean createDir);

	/**
	 * An API to return the base folder path for imagemagick.
	 * 
	 * @param batchClassIdentifier {@link String}
	 * @param createDir boolean
	 * @return {@link String} searchClassSamplePath
	 */
	String getImageMagickBaseFolderPath(String batchClassIdentifier, boolean createDir);

	/**
	 * An API to return the Index folder for fuzzy db.
	 * 
	 * @param batchClassIdentifier {@link String}
	 * @param createDir boolean
	 * @return {@link String} searchClassSamplePath
	 */
	String getFuzzyDBIndexFolder(String batchClassIdentifier, boolean createDir);

	/**
	 * An API to return the complete export folder location path.
	 * 
	 * @return {@link String} exportFolderLocation.
	 */
	String getExportFolderLocation();

	/**
	 * An API to return the base http url path.
	 * 
	 * @return {@link String} base http url path.
	 */
	String getBaseHttpURL();

	/**
	 * An API to create the hocrPages object.
	 * 
	 * @param hocrPages {@link HocrPages}
	 * @param batchInstanceIdentifier {@link String}
	 * @param pageId {@link String}
	 */
	void createHocr(HocrPages hocrPages, String batchInstanceIdentifier, String pageId);

	/**
	 * An API to get the HocrPages object for an input of HOCR.XML.
	 * 
	 * @param batchInstanceIdentifier {@link Serializable}
	 * @param pageId {@link String}
	 * @return {@link HocrPages}
	 */
	HocrPages getHocrPages(Serializable batchInstanceIdentifier, String pageId);

	/**
	 * An API to get the Batch object for an input of batchInstanceIdentifier.
	 * 
	 * @param batchInstanceIdentifier {@link Serializable}
	 * 
	 * @return {@link Batch}
	 */
	Batch getBatch(Serializable batchInstanceIdentifier);

	/**
	 * An API to create the Batch object.
	 * 
	 * @param batch {@link Batch}
	 */
	void createBatch(Batch batch);

	/**
	 * An API to update the Batch object.
	 * 
	 * @param batch {@link Batch}
	 */
	void updateBatch(Batch batch);

	/**
	 * An API to update the Batch object in folder importer.
	 * 
	 * @param batch {@link Batch}
	 * @param isFirstTimeUpdate boolean to create the batch xml/zip
	 */
	void updateBatch(Batch batch, boolean isFirstTimeUpdate);

	/**
	 * An API to fetch BatchClass by id.
	 * 
	 * @param identifier {@link Serializable}
	 * @param files {@link File}
	 */
	void storeFiles(Serializable identifier, File[] files);

	/**
	 * An API to get the backup files.
	 * 
	 * @param identifier {@link Serializable}
	 * @param files {@link File}
	 */
	void backUpFiles(Serializable identifier, File[] files);

	/**
	 * An API to get the URL object for the specified file name.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param fileName {@link String}
	 * @return URL object.
	 */
	URL getURL(String batchInstanceIdentifier, String fileName);

	/**
	 * An API to get the URL object for the batch ID.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @return URL object.
	 */
	URL getBatchContextURL(String batchInstanceIdentifier);

	/**
	 * An API to get the File object for the specified file name.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param fileName {@link String}
	 * @return File object.
	 */
	File getFile(String batchInstanceIdentifier, String fileName);

	/**
	 * An API to get the input stream object for the specified file name.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param fileName {@link String}
	 * @return InputStream object.
	 */
	InputStream getInputStream(String batchInstanceIdentifier, String fileName);

	/**
	 * An API to merge two different documents to a single document. This will merge all the pages of second document to first
	 * document.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param docIDOne {@link String}
	 * @param mergeDocID {@link String}
	 * @throws DCMAApplicationException If any of the input parameter is null.
	 * @return Batch
	 */
	Batch mergeDocuments(String batchInstanceIdentifier, String docIDOne, String mergeDocID) throws DCMAApplicationException;

	/**
	 * An API to swap one page of one document to second page of other document.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param swapDocIDOne {@link String}
	 * @param swapPageIDOne {@link String}
	 * @param swapDocIDTwo {@link String}
	 * @param swapPageIDTwo {@link String}
	 * @throws DCMAApplicationException If not able to find swapDocIDOne, swapPageIDOne, swapDocIDTwo or swapPageIDTwo. If not able to
	 *             find all swapPageIDOne and swapPageIDTwo within swapDocIDOne and swapDocIDTwo respectively.
	 */
	void swapPageOfDocuments(String batchInstanceIdentifier, String swapDocIDOne, String swapPageIDOne, String swapDocIDTwo,
			String swapPageIDTwo) throws DCMAApplicationException;

	/**
	 * An API to swap pages of document. This will swap the pages for the input document id.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param docID {@link String}
	 * @param swapPageIDOne {@link String}
	 * @param swapPageIDTwo {@link String}
	 * @throws DCMAApplicationException If not able to find docID, swapPageIDOne or swapPageIDTwo. If not able to find all
	 *             swapPageIDOne and swapPageIDTwo within docID.
	 */
	void swapPageOfDocument(String batchInstanceIdentifier, String docID, String swapPageIDOne, String swapPageIDTwo)
			throws DCMAApplicationException;

	/**
	 * An API to split the document for given input page id. This will create a new document starting from input page id to the last
	 * page id.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param docID {@link String}
	 * @param pageID {@link String}
	 * @throws DCMAApplicationException If any one of the parameter is null or pageID is not present in docID.
	 */
	void splitDocument(String batchInstanceIdentifier, String docID, String pageID) throws DCMAApplicationException;

	/**
	 * An API to re order pages of document. This will re order the pages for the input document id.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param docID {@link String}
	 * @param reOrderOfPageIDs {@link String}
	 * @throws DCMAApplicationException If not able to find docID, reOrderOfPageIDs. If not able to find all reOrderOfPageIDs within
	 *             duplicatePageID.
	 */
	void reOrderPagesOfDocument(String batchInstanceIdentifier, String docID, List<String> reOrderOfPageIDs)
			throws DCMAApplicationException;

	/**
	 * An API to create duplicate page of document. This will create duplicate for all the information available to page. This will
	 * create duplicate for all the files present at page level fields.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param docID {@link String}
	 * @param duplicatePageID {@link String}
	 * @throws DCMAApplicationExceptionIf not able to find docID, duplicatePageID. If not able to find removePageID within
	 *             duplicatePageID.
	 */
	void duplicatePageOfDocument(String batchInstanceIdentifier, String docID, String duplicatePageID) throws DCMAApplicationException;

	/**
	 * An API to remove the page of document. This will remove all the information available to page. This will delete all the files
	 * present at page level fields. If a document have single page and we are deleting that page means delete the whole document.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param docID {@link String}
	 * @param removePageID {@link String}
	 * @throws DCMAApplicationException If not able to find docID, removePageID. If not able to find removePageID within docID.
	 */
	void removePageOfDocument(String batchInstanceIdentifier, String docID, String removePageID) throws DCMAApplicationException;

	/**
	 * An API to move the page of one document to to another document. Position of the page in new document will depend on the to page
	 * ID. If the isAfterToPageID boolean is true then from page ID is added after the to page ID other wise before the to page ID.
	 * After the movement API will delete the from page ID from the from doc ID.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param fromDocID {@link String}
	 * @param fromPageID {@link String}
	 * @param toDocID {@link String}
	 * @param toPageID {@link String} from page ID position relative to this page ID.
	 * @param isAfterToPageID {@link boolean} true means add after the to page ID. false means add before the to page ID.
	 * @throws DCMAApplicationException If not able to find fromDocID, fromPageID or toDocID. If not able to find fromPageID within
	 *             fromDocID.
	 */
	void movePageOfDocument(String batchInstanceIdentifier, String fromDocID, String fromPageID, String toDocID, String toPageID,
			boolean isAfterToPageID) throws DCMAApplicationException;

	/**
	 * An API to update the document type name and document level fields for the input doc type ID.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param docID {@link String}
	 * @param docTypeName {@link String}
	 * @param documentLevelFields {@link DocumentLevelFields}
	 * @throws DCMAApplicationExceptionIf any one of the parameter is null or docID is not present in batch.
	 */
	void updateDocTypeName(String batchInstanceIdentifier, String docID, String docTypeName, DocumentLevelFields documentLevelFields)
			throws DCMAApplicationException;

	/**
	 * An API which will return true if the review is required other wise false.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @return boolean true if isReviewRequired other wise false.
	 * @throws DCMAApplicationException If any of the parameter is not valid.
	 */
	boolean isReviewRequired(final String batchInstanceIdentifier) throws DCMAApplicationException;

	/**
	 * An API which will return true if the review is required other wise false.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @return boolean true if isReviewRequired other wise false.
	 * @throws DCMAApplicationException If any of the parameter is not valid.
	 */
	boolean isReviewRequired(final String batchInstanceIdentifier, boolean checkReviewFlag) throws DCMAApplicationException;

	/**
	 * An API which will return true if the validation is required for the document level fields other wise false.
	 * 
	 * @param batchInstanceIdentifier String
	 * @return boolean true if isValidationRequired other wise false.
	 * @throws DCMAApplicationException If any of the parameter is not valid.
	 */
	boolean isValidationRequired(final String batchInstanceIdentifier) throws DCMAApplicationException;

	/**
	 * An API to generate all the folder structure for samples.
	 * 
	 * @param batchClassIDList {@link List<{@link List<{@link String}>}>}
	 * @throws DCMAApplicationException If any of the parameter is not valid.
	 */
	void sampleGeneration(final List<List<String>> batchIdDocPgNameList) throws DCMAApplicationException;

	/**
	 *An API to delete the folders.
	 * 
	 * @param docTypeNameList {@link List<{@link String}>}the list of name of folders that have been deleted
	 * @param batchClassIdentifier {@link String} the batch class name from which the folders are deleted
	 * @throws DCMAApplicationException if any of the parameter is not valid
	 */
	void deleteDocTypeFolder(List<String> docTypeNameList, String batchClassIdentifier) throws DCMAApplicationException;

	/**
	 * An API to get the thumbnail file path.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param documentId {@link String}
	 * @param pageId {@link String}
	 * @return String
	 * @throws DCMAApplicationException
	 */
	String getThumbnailFilePath(String batchInstanceIdentifier, String documentId, String pageId) throws DCMAApplicationException;

	/**
	 * An API to get display image file path.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @param documentId {@link String}
	 * @param pageId {@link String}
	 * @return String
	 * @throws DCMAApplicationException
	 */
	String getDisplayImageFilePath(String batchInstanceIdentifier, String documentId, String pageId) throws DCMAApplicationException;

	/**
	 * An API to fetch the image URL's excluding the imagename.
	 * 
	 * @return the URL to be appended to the image name.
	 */
	String getWebScannerScannedImagesURL();

	/**
	 * An API to fetch the folder in which the scanned images are being kept.
	 * 
	 * @return the scanned images folder path.
	 */
	String getWebScannerScannedImagesFolderPath();

	/**
	 * API to get the base folder path.
	 * 
	 * @return base folder absolute path.
	 */
	String getBaseSampleFDLock();

	/**
	 * API to create a temp folder for scanned images for the given name.
	 * 
	 * @param folderName {@link String}
	 * @throws DCMAApplicationException
	 */
	void createWebScannerFolder(String folderName) throws DCMAApplicationException;

	/**
	 * API to copy all the .tiff and .tif files from the sourcePath+folderName to the batchPath+folderName.
	 * 
	 * @param sourcePath {@link String} the path containing the folder to be copied
	 * @param folderName {@link String} the folder to be copied.
	 * @param batchClassID {@link String} the identifier of batch to which the batch is to be copied
	 * @throws DCMAApplicationException
	 */
	void copyFolder(String sourcePath, String folderName, String batchClassID) throws DCMAApplicationException;

	/**
	 * API to return base folder location.
	 * 
	 * @return String
	 */
	String getBaseFolderLocation();

	/**
	 * An API to fetch project File Base Folder location.
	 * 
	 * @return String
	 */
	String getProjectFileBaseFolder();

	/**
	 * An API to fetch Email Base Folder location using Http protocol.
	 * 
	 * @return String
	 */
	String getHttpEmailFolderPath();

	/**
	 * An API to fetch Batches Folder location.
	 * 
	 * @param batchInstanceIdentifier {@link String}
	 * @return String
	 */
	String getBatchFolderURL(String batchInstanceIdentifier);

	/**
	 * An API to fetch Email Base Folder location.
	 * 
	 * @return String
	 */
	String getEmailFolderPath();

	/**
	 * API to get test kv extraction folder path.
	 * 
	 * @param batchClassID {@link BatchClassID}
	 * @param createDirectory boolean
	 * @return String absolute folder location
	 */
	String getTestKVExtractionFolderPath(BatchClassID batchClassID, boolean createDirectory);

	/**
	 * This API is used to generate the HocrPage object for input hocr file.
	 * 
	 * @param pageName {@link String}
	 * @param pathOfHOCRFile {@link String}
	 * @param outputFilePath {@link String}
	 * @param batchClassIdentifier
	 * @param ocrEngineName
	 * @return {@link HocrPage}
	 */
	HocrPage generateHocrPage(String pageName, String pathOfHOCRFile, String outputFilePath, String batchClassIdentifier,
			String ocrEngineName);

	/**
	 * An API to return the complete export batch class folder location path.
	 * 
	 * @return String Batch Export Folder Location
	 */
	String getBatchExportFolderLocation();

	/**
	 * An API to return the Index folder name for fuzzy db.
	 * 
	 * @return String fuzzyDb Index Folder Path
	 */
	String getFuzzyDBIndexFolderName();

	/**
	 * An API to return the Index folder name for fuzzy db.
	 * 
	 * @return String fuzzyDb Index Folder Path
	 */
	String getImagemagickBaseFolderName();

	/**
	 * An API to return the Index folder name for fuzzy db.
	 * 
	 * @return String fuzzyDb Index Folder Path
	 */
	String getSearchSampleName();

	/**
	 * An API to return the Index folder name for fuzzy db.
	 * 
	 * @return String fuzzyDb Index Folder Path
	 */
	String getSearchIndexFolderName();

	/**
	 * An API to return the test kv_extraction sample folder name.
	 * 
	 * @return String test kv_extraction sample folder name
	 */
	String getTestKVExtractionFolderName();

	/**
	 * An API to return the Batch Class Serializable File.
	 * 
	 * @return String name of batch Class Serializable Description as appearing on the UI
	 */
	String getBatchClassSerializableFile();

	/**
	 * API to get File bound Plugin Mapping Folder Name.
	 * 
	 * @return String Name of filebound plugin mapping folder
	 */
	String getFileboundPluginMappingFolderName();

	/**
	 * API to get the temp folder location for a batch class.
	 * 
	 * @return {@link String}
	 */
	String getTempFolderName();

	/**
	 * API to copy all the files from the Email download folder to the UNC Folder.
	 * 
	 * @param sourcePath {@link String} the path containing the folder to be copied
	 * @param folderName {@link String} the folder to be copied.
	 * @param batchClassID {@link String} the identifier of batch to which the batch is to be copied
	 * @throws DCMAApplicationException
	 */
	void copyEmailFolderToUNC(String sourcePath, String folderName, String batchClassID) throws DCMAApplicationException;

	/**
	 * API to get test table folder path.
	 * 
	 * @param batchClassID {@link BatchClassID}
	 * @param createDirectory {@link boolean}
	 * @return {@link String} absolute folder location
	 */
	String getTestTableFolderPath(BatchClassID batchClassID, boolean createDirectory);

	/**
	 * An API to return the test table sample folder name.
	 * 
	 * @return {@link String} test table sample folder name
	 */
	String getTestTableFolderName();

	/**
	 * API to create the batch XML at the end of the specified plugin.
	 * 
	 * @param batchInstanceID {@link BatchInstanceID}
	 * @param pluginWorkflow {@link String}
	 */
	void backUpBatchXML(final BatchInstanceID batchInstanceID, final String pluginWorkflow);

	/**
	 * API to return the ValidationScriptName.
	 * 
	 * @return {@link String} ValidationScriptName
	 */
	String getValidationScriptName();

	/**
	 * API to return the threadpool lock folder. This folder signifies if any thread is currently running for this batch instance at
	 * any point of time.
	 * 
	 * @return {@link String} thread pool lock folder name
	 */
	String getThreadpoolLockFolderName();

	/**
	 * API to add new table.
	 * 
	 * @return {@link String} AddNewTableScriptName
	 */
	String getAddNewTableScriptName();

	/**
	 * API to set local folder.
	 * 
	 * @param localFolderLocation {@link String}
	 */
	void setLocalFolderLocation(String localFolderLocation);

	/**
	 * API to set local folder.
	 * 
	 * @param testFolderLocation {@link String}
	 */
	void setTestFolderLocation(String testFolderLocation);

	/**
	 * API to return test folder.
	 * 
	 * @return {@link String}
	 */
	String getTestFolderLocation();

	/**
	 * API to set base folder of the project.
	 * 
	 * @param baseFolderLocation {@link String}
	 */
	void setBaseFolderLocation(String baseFolderLocation);

	/**
	 * API to get the detached batch class module object for a batch class.
	 * 
	 * @param batchClassIdentifier {@link String}
	 * @param moduleName {@link String}
	 * @return {@link BatchClassModule}
	 */
	BatchClassModule getDetachedBatchClassModuleByName(String batchClassIdentifier, String moduleName);

	/**
	 * API to get Script Config Folder Name.
	 * 
	 * @return {@link String}
	 */
	String getScriptConfigFolderName();

	/**
	 * API to get the upload batch folder path.
	 * 
	 * @return {@link String}
	 */
	String getUploadBatchFolder();

	/**
	 * API to copy all the files from the sourcePath+folderName to the batchPath+folderName. Valid file types are specified by the
	 * filter.
	 * 
	 * @param sourcePath {@link String} the path containing the folder to be copied
	 * @param folderName {@link String} the folder to be copied.
	 * @param batchClassID {@link String} the identifier of batch to which the batch is to be copied
	 * @param fileFilter {@link CustomFileFilter} the file filter specifying the valid file types to copy
	 * @throws DCMAApplicationException
	 */
	void copyFolderWithFileFilter(String sourcePath, String folderName, String batchClassID, CustomFileFilter fileFilter)
			throws DCMAApplicationException;

	/**
	 * To check whether zip switch is on or not.
	 * @return boolean the switch value from application properties
	 */
	boolean isZipSwitchOn();

	/**
	 * API to get the Web services folder name.
	 * 
	 * @return {@link String}
	 */
	String getWebServicesFolderPath();

	/**
	 * API to get advanced test extraction folder path.
	 * 
	 * @param batchClassIdentifier {@link String}
	 * @param createDirectory {@link boolean}
	 * @return {@link String}
	 */
	String getTestAdvancedKvExtractionFolderPath(String batchClassIdentifier, boolean createDirectory);

	/**
	 * API to get advanced test extraction folder name.
	 * 
	 * @return {@link String}
	 */
	String getTestAdvancedKVExtractionFolderName();

	/**
	 * Method to generate the HOCR files for given html files for web services.
	 * 
	 * @param workingDir {@link String}
	 * @param pageID {@link String}
	 * @param pathOfHOCRFile {@link String}
	 * @param hocrPage {@link HocrPage}
	 * @throws IOException
	 * @throws TransformerException
	 * @throws XPathExpressionException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	void hocrGenerationAPI(String workingDir, String pageID, String pathOfHOCRFile, HocrPage hocrPage) throws IOException,
			XPathExpressionException, TransformerException, ParserConfigurationException, SAXException;

	/**
	 * API to update the batch object to the file path specified.
	 * 
	 * @param batch {@link Batch}
	 * @param filePath {@link String}
	 */
	void update(final Batch batch, final String filePath);

	/**
	 * API to get the HOCR Pages using the file path specified.
	 * 
	 * @param xmlFilePath {@link String}
	 * @return {@link HocrPages}
	 */
	HocrPages getHOCR(String xmlFilePath);

	/**
	 * API to get the advanced test table folder path.
	 * 
	 * @param batchClassId {@link String}
	 * @param createDir {@link boolean}
	 * @return String
	 */
	String getAdvancedTestTableFolderPath(final String batchClassId, final boolean createDir);

	/**
	 * API to get advanced test table folder name.
	 * 
	 * @return {@link String}
	 */
	String getAdvancedTestTableFolderName();

	/**
	 * DB Export plugin mapping folder name.
	 * 
	 * @return {@link String} DB Export plugin mapping folder name.
	 */
	String getDbExportMappingFolderName();
}
